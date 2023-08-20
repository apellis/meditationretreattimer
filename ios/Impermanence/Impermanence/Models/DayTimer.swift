//
//  DayTimer.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/14/23.
//

import Foundation

@MainActor
final class DayTimer: ObservableObject {
    struct Segment: Identifiable {
        let name: String
        let startTime: Date
        let endTime: Date
        let endBell: Bell
        var rang: Bool = false
        let id = UUID()
    }

    @Published var activeSegmentName = ""
    @Published var activeSegmentTimeElapsed: TimeInterval = 0
    @Published var activeSegmentTimeRemaining: TimeInterval = 0
    @Published var segmentIndex: Int = -1
    private(set) var segments: [DayTimer.Segment] = []
    let startTime: Date
    private var freshTimer = true  // suppress bell upon segment change if true

    var segmentChangedAction: (() -> Void)?

    private weak var timer: Timer?
    private var frequency: TimeInterval { 1.0 / 60.0 }
    var segmentText: String {
        switch segmentIndex {
        case -2:
            return "(Day is empty)"
        case -1:
            return "(Day has not started yet)"
        case 0...segments.count-1:
            return "Now: \(segments[segmentIndex].name)"
        default:
            return "(Day is over)"
        }
    }

    init(startTime: TimeInterval = 0, segments: [Day.Segment] = []) {
        self.startTime = Calendar.current.startOfDay(for: Date.now).addingTimeInterval(startTime)

        guard !segments.isEmpty else { return }
        var newSegments: [DayTimer.Segment] = []
        var timeCursor = self.startTime
        segments.forEach { segment in
            newSegments.append(DayTimer.Segment(name: segment.name, startTime: timeCursor, endTime: timeCursor.addingTimeInterval(segment.duration), endBell: segment.endBell))
            timeCursor.addTimeInterval(segment.duration)
        }
        self.segments = newSegments
        activeSegmentName = segmentText
    }

    func startDay() {
        timer = Timer.scheduledTimer(withTimeInterval: frequency, repeats: true) { [weak self] timer in
            self?.update()
        }
        timer?.tolerance = 0.1
    }

    func stopDay() {
        timer?.invalidate()
    }

    private func changeToSegment(at index: Int) {
        if index > 0 {
            segments[index - 1].rang = true
        }
        segmentIndex = index
        activeSegmentName = segmentText

        guard index < segments.count && index >= 0 else { return }
        activeSegmentTimeElapsed = Date.now.timeIntervalSinceReferenceDate - self.segments[index].startTime.timeIntervalSinceReferenceDate
        activeSegmentTimeRemaining = segments[segmentIndex].endTime.timeIntervalSince(Date.now)
    }

    nonisolated private func update() {
        Task { @MainActor in
            var newSegmentIndex: Int = -1
            if self.segments.isEmpty {
                newSegmentIndex = -2
            } else if Date.now < self.startTime {
                newSegmentIndex = -1
            } else if Date.now > self.segments[self.segments.count - 1].endTime {
                newSegmentIndex = self.segments.count
            } else {
                segments.enumerated().forEach { (index, segment) in
                    if Date.now >= segment.startTime && Date.now < segment.endTime {
                        newSegmentIndex = index
                    }
                }
            }

            if self.segmentIndex != newSegmentIndex {
                changeToSegment(at: newSegmentIndex)
                if !freshTimer {
                    segmentChangedAction?()
                }
            }

            if self.segmentIndex >= 0 && self.segmentIndex < self.segments.count {
                activeSegmentTimeElapsed = Date.now.timeIntervalSinceReferenceDate - self.segments[self.segmentIndex].startTime.timeIntervalSinceReferenceDate
                activeSegmentTimeRemaining = segments[self.segmentIndex].endTime.timeIntervalSince(Date.now)
            } else {
                activeSegmentTimeElapsed = -1
                activeSegmentTimeRemaining = -1
            }
            self.freshTimer = false
        }
    }
}

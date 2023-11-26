//
//  DayActiveView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/12/23.
//

import SwiftUI
import AVFoundation
import UIKit

struct DayActiveView: View {
    @Binding var day: Day
    @StateObject var dayTimer: DayTimer

    @AppStorage("preventScreenSleep") private var preventScreenSleep = true

    private var player: AVPlayer { AVPlayer.bellPlayer }

    init(day: Binding<Day>) {
        self._day = day

        _dayTimer = StateObject(wrappedValue: DayTimer(startTime: day.wrappedValue.startTime, segments: day.wrappedValue.segments))
    }

    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 16.0)
                .fill(day.theme.mainColor)
            VStack {
                Text("\(dayTimer.segmentText)")
                    .font(.headline)
                    .padding(.top)
                if dayTimer.activeSegmentTimeElapsed >= 0 && dayTimer.activeSegmentTimeRemaining >= 0 {
                    SegmentProgressView(timeElapsed: dayTimer.activeSegmentTimeElapsed, timeRemaining: dayTimer.activeSegmentTimeRemaining, theme: day.theme)
                }
                Divider()
                    .frame(height: 2)
                    .overlay(day.theme.accentColor)
                ScrollView {
                    Text("Agenda")
                        .font(.headline)
                        .padding(.top)
                    ForEach(day.segments.indices, id: \.self) { i in
                        SegmentCardView(segment: day.segments[i], startTime: day.segmentStartEndTimes[i].0, endTime: day.segmentStartEndTimes[i].1, theme: day.theme, useTheme: true, highlighted: i == dayTimer.segmentIndex)
                    }
                }
                .padding()
            }
        }
        .padding()
        .foregroundColor(day.theme.accentColor)
        .onAppear {
            dayTimer.segmentChangedAction = {
                player.seek(to: .zero)
                player.play()
            }
            dayTimer.startDay()
            if preventScreenSleep {
                UIApplication.shared.isIdleTimerDisabled = true
            }
        }
        .onDisappear {
            dayTimer.stopDay()
            UIApplication.shared.isIdleTimerDisabled = false
        }
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct DayActiveView_Previews: PreviewProvider {
    static var previews: some View {
        DayActiveView(day: .constant(Day.fullDay))
    }
}

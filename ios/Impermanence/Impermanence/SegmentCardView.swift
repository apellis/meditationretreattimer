//
//  SegmentCardView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/5/23.
//

import SwiftUI

struct SegmentCardView: View {
    let segment: Day.Segment
    let startTime: Date
    let endTime: Date
    let theme: Theme
    let useTheme: Bool
    let highlighted: Bool
    let formatter = DateFormatter()

    init(segment: Day.Segment, startTime: Date, endTime: Date, theme: Theme, useTheme: Bool = false, highlighted: Bool = false) {
        self.segment = segment
        self.startTime = startTime
        self.endTime = endTime
        self.theme = theme
        self.useTheme = useTheme
        self.highlighted = highlighted

        formatter.dateFormat = "HH:mm"
    }

    var body: some View {
        HStack {
            Text(segment.name)
                .font(.headline)
                .accessibilityAddTraits(.isHeader)
            Spacer()
            Label("\(formatter.string(from: startTime)) – \(formatter.string(from: endTime))", systemImage: "clock")
                .font(.caption)
                .accessibilityLabel("duration \(segment.duration)")
            Spacer()
            Label("\(segment.endBell.numRings)", systemImage: "bell")
                .labelStyle(.trailingIcon)
                .font(.caption)
        }
        .padding()
        .if(self.useTheme) { $0.background(theme.mainColor) }
        .if(self.useTheme) { $0.foregroundColor(theme.accentColor) }
        .if(self.useTheme && self.highlighted) {
            $0.overlay(
                RoundedRectangle(cornerRadius: 10)
                    .strokeBorder(theme.accentColor, lineWidth: 1)
            )
        }
    }
}

extension View {
    @ViewBuilder
    func `if`<Transform: View>(_ condition: Bool, transform: (Self) -> Transform) -> some View {
        if condition {
            transform(self)
        } else {
            self
        }
    }
}

struct SegmentCardView_Previews: PreviewProvider {
    static var segment = Day.Segment(name: "Sit", duration: TimeInterval(45 * 60), endBell: Bell.singleBell)
    static var previews: some View {
        SegmentCardView(segment: segment, startTime: Date(), endTime: Date().addingTimeInterval(segment.duration), theme: .sky)
            .previewLayout(.fixed(width: 400, height: 60))
    }
}

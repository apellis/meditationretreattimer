//
//  DayCardView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/5/23.
//

import SwiftUI

struct DayCardView: View {
    let day: Day
    let formatter = DateFormatter()

    init(day: Day) {
        self.day = day

        formatter.dateFormat = "HH:mm"
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(day.name)
                .font(.headline)
                .accessibilityAddTraits(.isHeader)
            Spacer()
            HStack {
                if day.segments.count > 0 {
                    Label("\(formatter.string(from: day.segmentStartEndTimes[0].0)) – \(formatter.string(from: day.segmentStartEndTimes[day.segmentStartEndTimes.count - 1].0))", systemImage: "clock")
                        .accessibilityLabel("duration \(day.segments.count)")
                        .labelStyle(.trailingIcon)
                }
                Spacer()
            }
            .font(.caption)
        }
        .padding()
        .foregroundColor(day.theme.accentColor)
    }
}

struct DayCardView_Previews: PreviewProvider {
    static var day = Day.fullDay
    static var previews: some View {
        DayCardView(day: day)
            .background(day.theme.mainColor)
            .previewLayout(.fixed(width: 400, height: 60))
    }
}

//
//  DayDetailEditView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/12/23.
//

import SwiftUI

struct DayDetailEditView: View {
    @Binding var day: Day
    @State private var newSegmentName = ""
    @State private var newSegmentHours: Int = 0
    @State private var newSegmentMinutes: Int = 0

    var body: some View {
        Form {
            Section(header: Text("Day Info")) {
                TextField("Name", text: $day.name)
                DatePicker("Start of Day", selection: $day.startTimeAsDate, displayedComponents: .hourAndMinute)
                    .accessibilityValue("\(day.startTimeAsDate)")
                ThemePicker(selection: $day.theme)
            }
            Section(header:
                VStack(alignment: .leading) {
                    Text("Segments").fontWeight(.bold)
                    // TODO add a help button using SF Symbol questionmark.circle with instructions for editing
                }
            ) {
                List {
                    ForEach(day.segments.indices, id: \.self) { i in
                        SegmentCardView(segment: day.segments[i], startTime: day.segmentStartEndTimes[i].0, endTime: day.segmentStartEndTimes[i].1, theme: day.theme)
                    }
                    .onDelete { indices in
                        day.segments.remove(atOffsets: indices)
                    }
                    .onMove { from, to in
                        day.segments.move(fromOffsets: from, toOffset: to)
                    }
                }
            }
            Section(header: Text("New Segment")) {
                VStack {
                    TextField("New Segment Name", text: $newSegmentName)
                    HStack {
                        Picker("", selection: $newSegmentHours) {
                            ForEach(0..<24, id: \.self) { i in
                                Text("\(i)h").tag(i)
                            }
                        }
                        .pickerStyle(WheelPickerStyle())
                        Picker("", selection: $newSegmentMinutes) {
                            ForEach(0..<60, id: \.self) { i in
                                Text("\(i)min").tag(i)
                            }
                        }
                        .pickerStyle(WheelPickerStyle())
                    }
                    .padding(.horizontal)
                    .accessibilityElement(children: .combine)
                    .accessibilityValue("\(newSegmentHours) hours, \(newSegmentMinutes) minutes")
                    Button(action: {
                        withAnimation {
                            let segment = Day.Segment(name: newSegmentName, duration: TimeInterval(60 * 60 * newSegmentHours + 60 * newSegmentMinutes))
                            day.segments.append(segment)
                            newSegmentName = ""
                            newSegmentHours = 0
                            newSegmentMinutes = 0
                        }
                    }) {
                        Text("Add")
                            .accessibilityLabel("Add segment")
                    }
                    .disabled(newSegmentName.isEmpty || newSegmentHours < 0 || newSegmentMinutes < 0 || newSegmentHours + newSegmentMinutes == 0)
                }
            }
        }
    }
}

struct DayDetailEditView_Previews: PreviewProvider {
    static var previews: some View {
        DayDetailEditView(day: .constant(Day.fullDay))
    }
}

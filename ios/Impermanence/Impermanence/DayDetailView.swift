//
//  DayDetailView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/12/23.
//

import SwiftUI

struct DayDetailView: View {
    @Binding var day: Day

    let calendar: Calendar = Calendar.current
    let formatter = DateFormatter()
    var startTime = Date.now
    var endTime = Date.now

    @State private var editingDay = Day.emptyDay
    @State private var isPresentingEditView = false

    init(day: Binding<Day>) {
        self._day = day

        self.startTime = calendar.startOfDay(for: Date.now)
            .addingTimeInterval(self.day.startTime)
        self.endTime = calendar.startOfDay(for: Date.now)
            .addingTimeInterval(self.day.startEndTimeIntervals.1)

        formatter.dateFormat = "HH:mm"
    }

    var body: some View {
        List {
            Section(header: Text("Day Info")) {
                NavigationLink(destination: DayActiveView(day: $day)) {
                    Label("Start or Resume Day", systemImage: "play.circle")
                        .font(.headline)
                        .foregroundColor(.accentColor)
                }
                Label("\(formatter.string(from: self.startTime))–\(formatter.string(from: self.endTime))", systemImage: "clock")
                HStack {
                    Label("Theme", systemImage: "paintpalette")
                    Spacer()
                    Text(day.theme.name)
                        .padding(4)
                        .foregroundColor(day.theme.accentColor)
                        .background(day.theme.mainColor)
                        .cornerRadius(4)
                }
                .accessibilityElement(children: .combine)
            }
            Section(header: Text("Segments")) {
                ForEach(day.segments.indices, id: \.self) { i in
                    SegmentCardView(segment: day.segments[i], startTime: day.segmentStartEndTimes[i].0, endTime: day.segmentStartEndTimes[i].1, theme: day.theme)
                }
            }
        }
        .navigationTitle(day.name)
        .toolbar {
            Button("Edit") {
                isPresentingEditView = true
                editingDay = day
            }
        }
        .sheet(isPresented: $isPresentingEditView) {
            NavigationStack {
                DayDetailEditView(day: $editingDay)
                    .navigationTitle(day.name)
                    .toolbar {
                        ToolbarItem(placement: .cancellationAction) {
                            Button("Cancel") {
                                isPresentingEditView = false
                            }
                        }
                        ToolbarItem(placement: .confirmationAction) {
                            Button("Done") {
                                isPresentingEditView = false
                                day = editingDay
                            }
                        }
                    }
            }
        }
    }
}

struct DayDetailView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            DayDetailView(day: .constant(Day.fullDay))
        }
    }
}

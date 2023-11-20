//
//  NewDaySheet.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/16/23.
//

import SwiftUI

import NavigationStackBackport

struct NewDaySheet: View {
    @State private var newDay = Day.emptyDay
    @Binding var days: [Day]
    @Binding var isPresentingNewDayView: Bool

    var body: some View {
        NavigationStackBackport.NavigationStack {
            DayDetailEditView(day: $newDay)
                .toolbar {
                    ToolbarItem(placement: .cancellationAction) {
                        Button("Dismiss") {
                            isPresentingNewDayView = false
                        }
                    }
                    ToolbarItem(placement: .confirmationAction) {
                        Button("Add") {
                            days.append(newDay)
                            isPresentingNewDayView = false
                        }
                        .disabled(newDay.name == "")
                    }
                }
        }
    }
}

struct NewDaySheet_Previews: PreviewProvider {
    static var previews: some View {
        NewDaySheet(days: .constant([Day.openingDay, Day.fullDay]), isPresentingNewDayView: .constant(true))
    }
}

//
//  SettingsView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/15/23.
//

import SwiftUI

import NavigationStackBackport

struct SettingsView: View {
    @AppStorage("loopDays") private var loopDays = true
    @AppStorage("use24HourTime") private var use24HourTime = true
    @AppStorage("preventScreenSleep") private var preventScreenSleep = true

    @Binding var isPresentingSettingsView: Bool

    var body: some View {
        NavigationStackBackport.NavigationStack {
            /*
            Toggle(isOn: $loopDays, label: {
                Text("Days loop at midnight")
            })
            .padding()
             */
            Toggle(isOn: $use24HourTime, label: {
                Text("Use 24 hour time")
            })
            .padding()
            Toggle(isOn: $preventScreenSleep, label: {
                Text("Prevent screen from sleeping when a day is running")
            })
            .padding()
            .navigationTitle("Settings")
            .toolbar {
                    ToolbarItem(placement: .cancellationAction) {
                        Button(action: {
                            isPresentingSettingsView = false
                        }) {
                            Image(systemName: "arrow.left")
                        }
                    }
            }
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(isPresentingSettingsView: .constant(true))
    }
}

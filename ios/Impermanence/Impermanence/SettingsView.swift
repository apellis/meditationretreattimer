//
//  SettingsView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/15/23.
//

import SwiftUI

struct SettingsView: View {
    @Binding var isPresentingSettingsView: Bool

    @State private var use24HourTime: Bool = false
    @State private var loopDays: Bool = true

    var body: some View {
        NavigationStack {
            Toggle(isOn: $use24HourTime, label: {
                Text("Use 24 hour time")
            })
            Toggle(isOn: $loopDays, label: {
                Text("Days loop at midnight")
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

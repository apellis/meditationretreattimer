//
//  SettingsView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/15/23.
//

import SwiftUI

import NavigationStackBackport

struct SettingsView: View {
    @Binding var isPresentingSettingsView: Bool

    @State private var use24HourTime: Bool = false

    var body: some View {
        NavigationStackBackport.NavigationStack {
            Toggle(isOn: $use24HourTime, label: {
                Text("Use 24 hour time")
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

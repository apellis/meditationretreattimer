//
//  ImpermanenceApp.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/5/23.
//

import SwiftUI

@main
struct ImpermanenceApp : App {
    @StateObject private var store = DayStore()

    var body: some Scene {
        WindowGroup {
            DaysView(days: $store.days) {
                Task {
                    do {
                        try await store.save(days: store.days)
                    } catch {
                        fatalError(error.localizedDescription)
                    }
                }
            }
                .task {
                    do {
                        try await store.load()
                    } catch {
                        fatalError(error.localizedDescription)
                    }
                }
        }
    }
}

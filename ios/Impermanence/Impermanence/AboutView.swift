//
//  AboutView.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/15/23.
//

import SwiftUI

struct AboutView: View {
    @Binding var isPresentingAboutView: Bool

    var body: some View {
        NavigationStack {
            VStack{
                Text("For the benefit of all beings ☸️")
                Divider()
                    .padding()
                Text("Copyright 2023, Neversink LLC")
                Text("Contact: apellis@gmail.com")
                Divider()
                    .padding()
                Text("Future features")
                Text("""
                    * More bell sound options
                    * Control over number of bell chimes per transition
                    * 12- vs. 24-hour clock setting
                    * (your feature request can go here!)
                    """)
                    .padding()
                .toolbar {
                    ToolbarItem(placement: .cancellationAction) {
                        Button(action: {
                            isPresentingAboutView = false
                        }) {
                            Image(systemName: "arrow.left")
                        }
                    }
                }
                .navigationTitle("About")
            }
        }
    }
}

struct AboutView_Previews: PreviewProvider {
    static var previews: some View {
        AboutView(isPresentingAboutView: .constant(true))
    }
}

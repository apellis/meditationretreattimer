//
//  BellPlayer.swift
//  Impermanence
//
//  Created by Alex Ellis on 8/16/23.
//

import Foundation
import AVFoundation

extension AVPlayer {
    static let bellPlayer: AVPlayer = {
        guard let url = Bundle.main.url(forResource: "bell", withExtension: "mp3") else { fatalError("Failed to find sound file.") }
        return AVPlayer(url: url)
    }()
}

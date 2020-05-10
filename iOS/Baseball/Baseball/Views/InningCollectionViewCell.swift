//
//  InningCollectionViewCell.swift
//  Baseball
//
//  Created by jinie on 2020/05/09.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class InningCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var inningLabel: InningLabel!
    
    static let identifier = "InningCell"
    
    func showLabelBackground() {
        inningLabel.backgroundColor = .white
        inningLabel.textColor = .systemBlue
    }
    
    func hideLabelBackground() {
        inningLabel.backgroundColor = .clear
        inningLabel.textColor = .white
    }
    
}

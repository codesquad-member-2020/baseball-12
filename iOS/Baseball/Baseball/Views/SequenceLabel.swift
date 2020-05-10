//
//  SequenceLabel.swift
//  Baseball
//
//  Created by jinie on 2020/05/10.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class SequenceLabel: UILabel {

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        configure()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        configure()
    }
    
    private func configure() {
        layer.cornerRadius = bounds.height / 2 + 1
        layer.masksToBounds = true
    }

}

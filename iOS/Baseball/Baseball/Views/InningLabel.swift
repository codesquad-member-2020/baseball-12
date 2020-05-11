//
//  InningLabel.swift
//  Baseball
//
//  Created by jinie on 2020/05/10.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class InningLabel: UILabel {

    private let insets = UIEdgeInsets(top: 5, left: 12, bottom: 5, right: 12)
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        configure()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        configure()
    }
    
    override func drawText(in rect: CGRect) {
        super.drawText(in: rect.inset(by: insets))
    }
    
    override var intrinsicContentSize: CGSize {
        let size = super.intrinsicContentSize
        return CGSize(width: size.width + insets.left + insets.right,
                      height: size.height + insets.top + insets.bottom)
    }
    
    private func configure() {
        layer.cornerRadius = 2.0
        layer.masksToBounds = true
    }

}

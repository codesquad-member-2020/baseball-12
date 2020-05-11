//
//  BadgeLabel.swift
//  Baseball
//
//  Created by jinie on 2020/05/10.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class BadgeLabel: UILabel {

    private let insets = UIEdgeInsets(top: 1, left: 4, bottom: 1, right: 4)
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    override func drawText(in rect: CGRect) {
        super.drawText(in: rect.inset(by: insets))
    }
    
    override var intrinsicContentSize: CGSize {
        let size = super.intrinsicContentSize
        return CGSize(width: size.width + insets.left + insets.right,
                      height: size.height + insets.top + insets.bottom)
    }

}

//
//  BallJudgmentView.swift
//  Baseball
//
//  Created by jinie on 2020/05/09.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class BallJudgmentView: UIView {

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        configure()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        configure()
    }
    
    private func configure() {
        let nib = UINib(nibName: "BallJudgmentView", bundle: nil)
        guard let view = nib.instantiate(withOwner: self, options: nil).first as? UIView else { return }
        view.frame = self.bounds
        self.addSubview(view)
    }

}

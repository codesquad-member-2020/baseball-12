//
//  BatterRecordView.swift
//  Baseball
//
//  Created by jinie on 2020/05/09.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class BatterRecordView: UIView {
    
    @IBOutlet weak var contentView: UIView!
    @IBOutlet weak var judgmentStackView: UIStackView!

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        configure()
        insert()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        configure()
        insert()
    }
    
    private func configure() {
        Bundle.main.loadNibNamed("BatterRecordView", owner: self, options: nil)
        addSubview(contentView)
        contentView.frame = self.bounds
    }
    
    private func insert() {
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
    }

}

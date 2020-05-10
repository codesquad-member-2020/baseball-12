//
//  BatterRecordView.swift
//  Baseball
//
//  Created by jinie on 2020/05/09.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class BatterRecordView: UIView {
    
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
        let nib = UINib(nibName: "BatterRecordView", bundle: nil)
        guard let view = nib.instantiate(withOwner: self, options: nil).first as? UIView else { return }
        view.frame = self.bounds
        self.addSubview(view)
    }
    
    private func insert() {
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
        judgmentStackView.insertArrangedSubview(BallJudgmentView(), at: 0)
    }

}

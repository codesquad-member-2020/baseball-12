//
//  PlayViewController.swift
//  Baseball
//
//  Created by jinie on 2020/05/06.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

class PlayViewController: UIViewController {
    
    @IBOutlet weak var inningCollectionView: UICollectionView!
    @IBOutlet weak var pitcherView: UIView!
    @IBOutlet weak var batterStackView: UIStackView!
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureCollectionView()
        insertRecord()
    }
    
    private func configureCollectionView() {
        inningCollectionView.dataSource = self
        inningCollectionView.delegate = self
    }
    
    private func insertRecord() {
        batterStackView.insertArrangedSubview(BatterRecordView(), at: 0)
    }

}

extension PlayViewController: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 9
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: InningCollectionViewCell.identifier, for: indexPath) as! InningCollectionViewCell
        cell.inningLabel.text = "\(indexPath.item + 1)회"
        if let selectedIndexPath = collectionView.indexPathsForSelectedItems?.first, selectedIndexPath == indexPath {
            cell.showLabelBackground()
        }
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let cell = collectionView.cellForItem(at: indexPath) as? InningCollectionViewCell {
            cell.showLabelBackground()
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
        if let cell = collectionView.cellForItem(at: indexPath) as? InningCollectionViewCell {
            cell.hideLabelBackground()
        }
    }
    
}

extension PlayViewController: UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 64, height: 50)
    }
    
}

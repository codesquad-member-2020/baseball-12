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

    override func viewDidLoad() {
        super.viewDidLoad()
        
        inningCollectionView.dataSource = self
        inningCollectionView.delegate = self
        inningCollectionView.reloadData()
    }

}

extension PlayViewController: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 9
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "inningCell", for: indexPath) as! InningCollectionViewCell
        cell.inningLabel.text = "\(indexPath.item + 1)회"
        return cell
    }
    
}

extension PlayViewController: UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 64, height: 50)
    }
    
}

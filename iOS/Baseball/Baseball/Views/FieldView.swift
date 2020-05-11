//
//  FieldView.swift
//  Baseball
//
//  Created by jinie on 2020/05/06.
//  Copyright © 2020 jinie. All rights reserved.
//

import UIKit

@IBDesignable
class FieldView: UIView {
    
    private struct Ratios {
        static let field: CGFloat = 0.35
        static let base: CGFloat = 0.15
        static let homePlate: CGFloat = 0.13
    }
    
    var field: UIBezierPath!
    var firstBase: UIBezierPath!
    var secondBase: UIBezierPath!
    var thirdBase: UIBezierPath!
    var homePlate: UIBezierPath!
    
    var halfOfDiagonal: CGFloat {
        return bounds.height * Ratios.field
    }
    var fieldCenter: CGPoint {
        return CGPoint(x: bounds.midX, y: bounds.midY)
    }
    var firstBaseCenter: CGPoint {
        return CGPoint(x: bounds.midX + halfOfDiagonal, y: bounds.midY)
    }
    var secondBaseCenter: CGPoint {
        return CGPoint(x: bounds.midX, y: bounds.midY - halfOfDiagonal)
    }
    var thirdBaseCenter: CGPoint {
        return CGPoint(x: bounds.midX - halfOfDiagonal, y: bounds.midY)
    }
    var homePlateCenter: CGPoint {
        return CGPoint(x: bounds.midX, y: bounds.midY + halfOfDiagonal)
    }
    
    private func pathForRhombus(center: CGPoint, distance: CGFloat) -> UIBezierPath {
        let path = UIBezierPath()
        path.move(to: CGPoint(x: center.x, y: center.y - distance))
        path.addLine(to: CGPoint(x: center.x + distance, y: center.y))
        path.addLine(to: CGPoint(x: center.x, y: center.y + distance))
        path.addLine(to: CGPoint(x: center.x - distance, y: center.y))
        path.close()
        path.lineWidth = 3.0
        UIColor.white.set()
        return path
    }
    
    private func pathForPentagon(center: CGPoint, distance: CGFloat) -> UIBezierPath {
        let path = UIBezierPath()
        path.move(to: CGPoint(x: center.x, y: center.y + distance))
        path.addLine(to: CGPoint(x: center.x - distance, y: center.y))
        path.addLine(to: CGPoint(x: center.x - distance, y: center.y - distance))
        path.addLine(to: CGPoint(x: center.x + distance, y: center.y - distance))
        path.addLine(to: CGPoint(x: center.x + distance, y: center.y))
        path.close()
        path.lineWidth = 3.0
        UIColor.white.set()
        return path
    }
    
    override func draw(_ rect: CGRect) {
        field = pathForRhombus(center: fieldCenter, distance: halfOfDiagonal)
        field.stroke()
        firstBase = pathForRhombus(center: firstBaseCenter, distance: halfOfDiagonal * Ratios.base)
        firstBase.fill()
        secondBase = pathForRhombus(center: secondBaseCenter, distance: halfOfDiagonal * Ratios.base)
        secondBase.fill()
        thirdBase = pathForRhombus(center: thirdBaseCenter, distance: halfOfDiagonal * Ratios.base)
        thirdBase.fill()
        homePlate = pathForPentagon(center: homePlateCenter, distance: halfOfDiagonal * Ratios.homePlate)
        homePlate.fill()
    }
    
}

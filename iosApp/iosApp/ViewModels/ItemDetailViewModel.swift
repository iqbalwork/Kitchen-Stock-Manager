import Foundation
import SharedLogic
import Combine

@MainActor
class ItemDetailViewModel: ObservableObject {
    @Published var item: PantryItem
    
    init(item: PantryItem) {
        self.item = item
    }
    
    func markAsUsed() {
        // Logic to decrease quantity
    }
    
    func increaseQuantity() {
        // Logic to increase quantity
    }
}

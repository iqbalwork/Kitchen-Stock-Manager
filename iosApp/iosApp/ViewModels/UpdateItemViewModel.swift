import Foundation
import SharedLogic
import Combine

@MainActor
class UpdateItemViewModel: ObservableObject {
    @Published var itemName: String
    @Published var selectedCategory: String
    @Published var quantity: Int
    @Published var unit: String
    @Published var purchaseDate: Date
    @Published var expiryDate: Date
    
    let categories = ["Produce", "Dairy", "Pantry", "Meat", "Frozen"]
    let units = ["Pieces (pcs)", "kg", "grams", "liters"]
    
    init(item: PantryItem) {
        self.itemName = item.productName
        self.selectedCategory = item.categoryName
        self.quantity = Int(item.quantity)
        self.unit = item.unit
        self.purchaseDate = Date()
        self.expiryDate = Date()
    }
    
    func updateDetails() {
        // Logic to update item
    }
    
    func deleteItem() {
        // Logic to delete item
    }
}

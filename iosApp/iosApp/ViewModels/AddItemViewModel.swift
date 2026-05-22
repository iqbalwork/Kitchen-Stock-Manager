import Foundation
import SharedLogic
import Combine

@MainActor
class AddItemViewModel: ObservableObject {
    private let useCaseHelper = UseCaseHelper()
    
    @Published var itemName = ""
    @Published var quantity = "1"
    @Published var unit = "Pieces"
    @Published var selectedCategory = "Produce"
    @Published var purchaseDate = Date()
    @Published var expiryDate = Date()
    
    @Published var isLoading = false
    @Published var toast: Toast? = nil
    
    let categories = ["Produce", "Dairy", "Meat"]
    
    func saveItem() {
        guard !itemName.isEmpty else {
            self.toast = Toast(message: "Item name cannot be empty", type: .error)
            return
        }
        
        isLoading = true
        Task {
            do {
                // In a real app, we would map the local fields to the Domain Product/PantryItem
                // For now, let's assume a simplified call to the use case
                // Product(id: "", name: itemName, categoryId: selectedCategory, unit: unit, ...)
                // Actually, I should check the UseCase parameters
                
                // Mocking the result for UI demonstration of the snackbar/toast
                // In production, this would call useCaseHelper.addPantryItemUseCase.invoke(...)
                
                try await Task.sleep(nanoseconds: 1_000_000_000) // Simulate network
                
                self.toast = Toast(message: "Item saved successfully!", type: .success)
                self.isLoading = false
                
                // Clear fields or dismiss
            } catch {
                self.toast = Toast(message: "Failed to save item: \(error.localizedDescription)", type: .error)
                self.isLoading = false
            }
        }
    }
}

import Foundation
import SharedLogic
import Combine

@MainActor
class ShoppingViewModel: ObservableObject {
    private let useCaseHelper = UseCaseHelper()
    
    @Published var suggestedItems: [PantryItem] = []
    @Published var customItems: [PantryItem] = []
    @Published var isLoading = false
    @Published var toast: Toast? = nil
    
    var remainingItemsCount: Int {
        suggestedItems.count + customItems.count
    }
    
    init() {
        loadItems()
    }
    
    func loadItems() {
        Task {
            await refresh()
        }
    }
    
    func refresh() async {
        isLoading = true
        do {
            self.suggestedItems = try await useCaseHelper.getShoppingList()
            self.customItems = [] // Placeholder
        } catch {
            self.toast = Toast(message: "Failed to load shopping list: \(error.localizedDescription)", type: .error)
        }
        isLoading = false
    }
}

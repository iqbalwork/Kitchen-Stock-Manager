import Foundation
import SharedLogic
import Combine

@MainActor
class InventoryViewModel: ObservableObject {
    private let useCaseHelper = UseCaseHelper()
    
    @Published var items: [PantryItem] = []
    @Published var selectedFilter: String = "All Items"
    @Published var isLoading = false
    @Published var toast: Toast? = nil
    
    let filters = ["All Items", "Produce", "Dairy", "Meat", "Pantry"]
    
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
            self.items = try await useCaseHelper.getInventoryItems()
        } catch {
            self.toast = Toast(message: "Failed to load inventory: \(error.localizedDescription)", type: .error)
        }
        isLoading = false
    }
}

import Foundation
import SharedLogic
import Combine

@MainActor
class ItemDetailViewModel: ObservableObject {
    @Published var item: PantryItem
    @Published var isLoading = false
    @Published var toast: Toast? = nil
    
    private let useCaseHelper = UseCaseHelper()
    private let itemId: String

    init(item: PantryItem) {
        self.item = item
        self.itemId = item.id
    }
    
    func refresh() async {
        isLoading = true
        do {
            self.item = try await useCaseHelper.getItemDetail(itemId: itemId)
        } catch {
            self.toast = Toast(message: "Failed to refresh item", type: .error)
        }
        isLoading = false
    }

    func markAsUsed() {
        // Logic to decrease quantity
    }
    
    func increaseQuantity() {
        // Logic to increase quantity
    }
}

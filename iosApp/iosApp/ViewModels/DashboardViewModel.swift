import Foundation
import SharedLogic
import Combine

@MainActor
class DashboardViewModel: ObservableObject {
    private let repository = MockPantryRepository()
    
    @Published var items: [PantryItem] = []
    @Published var searchText: String = ""
    
    init() {
        loadItems()
    }
    
    func loadItems() {
        self.items = repository.getDashboardItems()
    }
}

import Foundation
import SharedLogic
import Combine

@MainActor
class DashboardViewModel: ObservableObject {
    private let useCaseHelper = UseCaseHelper()
    
    @Published var items: [PantryItem] = []
    @Published var searchText: String = ""
    @Published var isLoading = false
    @Published var toast: Toast? = nil
    
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
            let data = try await useCaseHelper.getDashboardData()
            self.items = data.expiringSoon + data.runningLow
        } catch {
            self.toast = Toast(message: "Failed to load dashboard: \(error.localizedDescription)", type: .error)
        }
        isLoading = false
    }
}

import SwiftUI
import SharedLogic

struct DashboardPage: View {
    @StateObject private var viewModel = DashboardViewModel()
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                // Header
                VStack(alignment: .leading, spacing: 8) {
                    Text("Hello, Chef!")
                        .font(.system(size: 24, weight: .bold))
                    Text("Check your kitchen stock today")
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                    
                    SearchBar(text: $viewModel.searchText, placeholder: "Search ingredients...")
                        .padding(.top, 8)
                }
                .padding(.horizontal)
                
                // Expiring Soon
                ExpiringSoonCard()
                    .padding(.horizontal)
                
                // Running Low
                VStack(alignment: .leading, spacing: 16) {
                    HStack {
                        Text("Running Low")
                            .font(.system(size: 20, weight: .bold))
                        Spacer()
                        Button("View All") {
                            // Action
                        }
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(AppColors.primary)
                    }
                    .padding(.horizontal)
                    
                    VStack(spacing: 12) {
                        ForEach(viewModel.items, id: \.id) { item in
                            NavigationLink(destination: ItemDetailPage(viewModel: ItemDetailViewModel(item: item))) {
                                PantryItemRow(item: item)
                            }
                            .buttonStyle(PlainButtonStyle())
                        }
                    }
                    .padding(.horizontal)
                }
                
                // Categories
                CategoryGrid()
                    .padding(.horizontal)
                
                Spacer()
                    .frame(height: 100) // Space for FAB
            }
            .padding(.vertical)
        }
        .background(AppColors.background)
        .navigationBarHidden(true)
    }
}

#Preview {
    DashboardPage()
}

extension View {
    func height(_ height: CGFloat) -> some View {
        self.frame(height: height)
    }
}

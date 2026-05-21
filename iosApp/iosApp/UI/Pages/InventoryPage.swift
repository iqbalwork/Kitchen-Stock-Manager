import SwiftUI
import SharedLogic

struct InventoryPage: View {
    @StateObject private var viewModel = InventoryViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            // Header
            HStack {
                Text("Kitchen Inventory")
                    .font(.system(size: 20, weight: .bold))
                Spacer()
                Image(systemName: "person.circle.fill")
                    .resizable()
                    .frame(width: 32, height: 32)
                    .foregroundColor(.gray)
            }
            .padding()
            .background(AppColors.surface)
            
            // Filters
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(viewModel.filters, id: \.self) { filter in
                        FilterChip(
                            text: filter,
                            isSelected: viewModel.selectedFilter == filter,
                            action: { viewModel.selectedFilter = filter }
                        )
                    }
                }
                .padding(.horizontal)
                .padding(.vertical, 12)
            }
            
            // List
            ScrollView {
                VStack(spacing: 12) {
                    ForEach(viewModel.items, id: \.id) { item in
                        NavigationLink(destination: ItemDetailPage(viewModel: ItemDetailViewModel(item: item))) {
                            PantryItemRow(item: item)
                        }
                        .buttonStyle(PlainButtonStyle())
                    }
                }
                .padding(.horizontal)
                .padding(.bottom, 100)
            }
        }
        .background(AppColors.background)
        .navigationBarHidden(true)
    }
}

#Preview {
    InventoryPage()
}

struct FilterChip: View {
    let text: String
    let isSelected: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(.system(size: 14, weight: .medium))
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(isSelected ? AppColors.primary : AppColors.surface)
                .foregroundColor(isSelected ? .white : .black)
                .cornerRadius(20)
                .overlay(
                    RoundedRectangle(cornerRadius: 20)
                        .stroke(isSelected ? Color.clear : Color.gray.opacity(0.3), lineWidth: 1)
                )
        }
    }
}

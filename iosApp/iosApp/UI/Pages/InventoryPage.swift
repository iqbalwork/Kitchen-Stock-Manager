import SwiftUI
import SharedLogic

struct InventoryPage: View {
    @StateObject private var viewModel = InventoryViewModel()
    
    var body: some View {
        ZStack {
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
                .refreshable {
                    await viewModel.refresh()
                }
            }
            .background(AppColors.background)
            .navigationBarHidden(true)
            
            // Snackbar / Toast Overlay
            if let toast = viewModel.toast {
                VStack {
                    Spacer()
                    ToastView(toast: toast)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                        .onAppear {
                            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                                viewModel.toast = nil
                            }
                        }
                }
                .animation(.spring(), value: viewModel.toast != nil)
            }
        }
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

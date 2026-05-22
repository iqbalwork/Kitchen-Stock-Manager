import SwiftUI
import SharedLogic

struct ShoppingPage: View {
    @StateObject private var viewModel = ShoppingViewModel()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                // Header
                HStack {
                    Text("Kitchen Stock Manager")
                        .font(.system(size: 20, weight: .bold))
                    Spacer()
                    Image(systemName: "person.circle.fill")
                        .resizable()
                        .frame(width: 32, height: 32)
                        .foregroundColor(.gray)
                }
                .padding()
                .background(AppColors.surface)
                
                ScrollView {
                    VStack(alignment: .leading, spacing: 24) {
                        // Summary Section
                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                Text("Shopping List")
                                    .font(.system(size: 24, weight: .bold))
                                Spacer()
                                Button("Clear All") {
                                    // Action
                                }
                                .font(.system(size: 14, weight: .bold))
                                .foregroundColor(AppColors.primary)
                            }
                            
                            HStack(spacing: 6) {
                                Circle()
                                    .fill(AppColors.primary)
                                    .frame(width: 8, height: 8)
                                Text("\(viewModel.remainingItemsCount) items remaining")
                                    .font(.system(size: 14))
                                    .foregroundColor(.gray)
                            }
                        }
                        .padding(.horizontal)
                        
                        // Suggested Section
                        VStack(alignment: .leading, spacing: 12) {
                            HStack {
                                Image(systemName: "wand.and.stars")
                                    .foregroundColor(AppColors.secondary)
                                Text("SUGGESTED TO BUY")
                                    .font(.system(size: 12, weight: .bold))
                                    .foregroundColor(.gray)
                            }
                            .padding(.horizontal)
                            
                            VStack(spacing: 12) {
                                ForEach(viewModel.suggestedItems, id: \.id) { item in
                                    PantryItemRow(
                                        item: item,
                                        isShoppingMode: true,
                                        onCheckChanged: { _ in }
                                    )
                                }
                            }
                            .padding(.horizontal)
                        }
                        
                        // Custom Items Section
                        VStack(alignment: .leading, spacing: 12) {
                            Text("CUSTOM ITEMS")
                                .font(.system(size: 12, weight: .bold))
                                .foregroundColor(.gray)
                                .padding(.horizontal)
                            
                            VStack(spacing: 12) {
                                ForEach(viewModel.customItems, id: \.id) { item in
                                    PantryItemRow(
                                        item: item,
                                        isShoppingMode: true,
                                        onCheckChanged: { _ in },
                                        onEditClicked: { }
                                    )
                                }
                            }
                            .padding(.horizontal)
                        }
                        
                        Spacer()
                            .frame(height: 100)
                    }
                    .padding(.vertical)
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
    ShoppingPage()
}

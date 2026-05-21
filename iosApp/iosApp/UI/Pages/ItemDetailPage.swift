import SwiftUI
import SharedLogic

struct ItemDetailPage: View {
    @Environment(\.dismiss) var dismiss
    @StateObject var viewModel: ItemDetailViewModel
    @State private var showUpdateItem = false
    
    var body: some View {
        VStack(spacing: 0) {
            // Header
            HStack {
                Button(action: { dismiss() }) {
                    Image(systemName: "arrow.left")
                        .foregroundColor(.primary)
                }
                Spacer()
                Text("Item Details")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.primary)
                Spacer()
                Button(action: {}) {
                    Image(systemName: "ellipsis.vertical")
                        .foregroundColor(.primary)
                }
            }
            .padding()
            .background(AppColors.surface)
            
            ScrollView {
                VStack(alignment: .leading, spacing: 20) {
                    // Image Section
                    ZStack(alignment: .topTrailing) {
                        RoundedRectangle(cornerRadius: 24)
                            .fill(Color(hex: 0xF0F0F0))
                            .frame(height: 250)
                            .overlay(
                                Image(systemName: "leaf.fill") // Placeholder
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80)
                                    .foregroundColor(.gray.opacity(0.3))
                            )
                        
                        StatusBadge(text: "Expiring Soon", color: AppColors.expiringSoon)
                            .padding(16)
                    }
                    .padding(.horizontal)
                    
                    // Info Section
                    HStack(alignment: .top) {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(viewModel.item.name)
                                .font(.system(size: 28, weight: .bold))
                            Text("\(viewModel.item.category.name.capitalized) • Whole Foods Market")
                                .font(.system(size: 14))
                                .foregroundColor(.gray)
                        }
                        
                        Spacer()
                        
                        VStack {
                            Text(viewModel.item.quantity.components(separatedBy: " ").first ?? "0")
                                .font(.system(size: 20, weight: .bold))
                            Text(viewModel.item.quantity.components(separatedBy: " ").last ?? "units")
                                .font(.system(size: 12))
                        }
                        .padding(.horizontal, 12)
                        .padding(.vertical, 8)
                        .background(AppColors.primary)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                    }
                    .padding(.horizontal)
                    
                    // Stock Level Card
                    VStack(alignment: .leading, spacing: 12) {
                        HStack {
                            Text("STOCK LEVEL")
                                .font(.system(size: 12, weight: .bold))
                                .foregroundColor(.gray)
                            Spacer()
                            Text("3 of 6 remaining")
                                .font(.system(size: 12))
                                .foregroundColor(.gray)
                        }
                        
                        // Custom Progress Bar
                        GeometryReader { geometry in
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 4)
                                    .fill(Color(hex: 0xEEEEEE))
                                    .frame(height: 12)
                                
                                RoundedRectangle(cornerRadius: 4)
                                    .fill(Color(hex: 0x9B8E1A)) // Gold color from screenshot
                                    .frame(width: geometry.size.width * 0.5, height: 12)
                            }
                        }
                        .frame(height: 12)
                        
                        // Mark as Used Action
                        HStack(spacing: 12) {
                            HStack {
                                Button(action: { viewModel.markAsUsed() }) {
                                    Image(systemName: "minus")
                                        .frame(width: 40, height: 48)
                                }
                                
                                Text("Mark as Used (1)")
                                    .font(.system(size: 16, weight: .bold))
                                    .frame(maxWidth: .infinity)
                                
                                Spacer()
                                    .frame(width: 40)
                            }
                            .background(AppColors.primary)
                            .foregroundColor(.white)
                            .cornerRadius(12)
                            
                            Button(action: { viewModel.increaseQuantity() }) {
                                Image(systemName: "plus")
                                    .frame(width: 48, height: 48)
                                    .background(AppColors.background)
                                    .foregroundColor(.primary)
                                    .cornerRadius(12)
                                    .overlay(
                                        RoundedRectangle(cornerRadius: 12)
                                            .stroke(Color.gray.opacity(0.2), lineWidth: 1)
                                    )
                            }
                        }
                        .padding(.top, 8)
                    }
                    .padding(20)
                    .background(AppColors.surface)
                    .cornerRadius(24)
                    .shadow(color: Color.black.opacity(0.05), radius: 10, x: 0, y: 5)
                    .padding(.horizontal)
                }
                .padding(.vertical)
            }
        }
        .background(AppColors.background)
        .navigationBarHidden(true)
        .overlay(alignment: .bottom) {
             // Just for navigation to Update Item
             Button("Edit Details") {
                 showUpdateItem = true
             }
             .padding()
             .opacity(0) // Hidden button to trigger navigation
        }
        .sheet(isPresented: $showUpdateItem) {
            UpdateItemPage(viewModel: UpdateItemViewModel(item: viewModel.item))
        }
    }
}

#Preview {
    ItemDetailPage(viewModel: ItemDetailViewModel(item: MockPantryRepository().getInventoryItems().first!))
}

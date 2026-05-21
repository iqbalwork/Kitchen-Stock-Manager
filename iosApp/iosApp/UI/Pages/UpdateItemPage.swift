import SwiftUI
import SharedLogic

struct UpdateItemPage: View {
    @Environment(\.dismiss) var dismiss
    @StateObject var viewModel: UpdateItemViewModel
    
    var body: some View {
        VStack(spacing: 0) {
            // Header
            HStack {
                Button(action: { dismiss() }) {
                    Image(systemName: "arrow.left")
                        .foregroundColor(.primary)
                }
                Spacer()
                Text("Update Item")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.primary)
                Spacer()
                Spacer().frame(width: 24) // Placeholder for symmetry
            }
            .padding()
            .background(AppColors.surface)
            
            ScrollView {
                VStack(alignment: .leading, spacing: 24) {
                    VStack(alignment: .leading, spacing: 20) {
                        Text("ITEM DETAILS")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.gray)
                        
                        // Item Name Field
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Item Name")
                                .font(.system(size: 14, weight: .medium))
                            HStack {
                                Image(systemName: "square.and.pencil")
                                    .foregroundColor(.gray)
                                TextField("Item Name", text: $viewModel.itemName)
                            }
                            .padding()
                            .background(AppColors.background)
                            .cornerRadius(12)
                        }
                        
                        // Category Chips
                        VStack(alignment: .leading, spacing: 12) {
                            Text("Category")
                                .font(.system(size: 14, weight: .medium))
                            ScrollView(.horizontal, showsIndicators: false) {
                                HStack(spacing: 12) {
                                    ForEach(viewModel.categories, id: \.self) { category in
                                        CategoryChipView(
                                            text: category,
                                            isSelected: viewModel.selectedCategory == category,
                                            action: { viewModel.selectedCategory = category }
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Quantity and Unit
                        HStack(spacing: 16) {
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Quantity")
                                    .font(.system(size: 14, weight: .medium))
                                HStack {
                                    Button(action: { if viewModel.quantity > 0 { viewModel.quantity -= 1 } }) {
                                        Image(systemName: "minus")
                                    }
                                    Spacer()
                                    Text("\(viewModel.quantity)")
                                        .fontWeight(.bold)
                                    Spacer()
                                    Button(action: { viewModel.quantity += 1 }) {
                                        Image(systemName: "plus")
                                    }
                                }
                                .padding()
                                .background(AppColors.background)
                                .cornerRadius(12)
                                .foregroundColor(.primary)
                            }
                            .frame(maxWidth: .infinity)
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Unit")
                                    .font(.system(size: 14, weight: .medium))
                                Menu {
                                    ForEach(viewModel.units, id: \.self) { unit in
                                        Button(unit) { viewModel.unit = unit }
                                    }
                                } label: {
                                    HStack {
                                        Text(viewModel.unit)
                                        Spacer()
                                        Image(systemName: "chevron.down")
                                            .font(.system(size: 12))
                                    }
                                    .padding()
                                    .background(AppColors.background)
                                    .cornerRadius(12)
                                    .foregroundColor(.primary)
                                }
                            }
                            .frame(maxWidth: .infinity)
                        }
                        
                        // Freshness Tracking
                        VStack(alignment: .leading, spacing: 16) {
                            HStack {
                                Image(systemName: "leaf.fill")
                                    .foregroundColor(AppColors.primary)
                                Text("FRESHNESS TRACKING")
                                    .font(.system(size: 12, weight: .bold))
                                    .foregroundColor(.gray)
                            }
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Purchase Date")
                                    .font(.system(size: 14, weight: .medium))
                                DatePicker("", selection: $viewModel.purchaseDate, displayedComponents: .date)
                                    .labelsHidden()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .padding()
                                    .background(Color(hex: 0xF8F9FA))
                                    .cornerRadius(12)
                            }
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Expiry Date (Est.)")
                                    .font(.system(size: 14, weight: .medium))
                                DatePicker("", selection: $viewModel.expiryDate, displayedComponents: .date)
                                    .labelsHidden()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .padding()
                                    .background(Color(hex: 0xF8F9FA))
                                    .cornerRadius(12)
                            }
                        }
                    }
                    .padding(24)
                    .background(AppColors.surface)
                    .cornerRadius(24)
                    .shadow(color: Color.black.opacity(0.05), radius: 10, x: 0, y: 5)
                    .padding(.horizontal)
                    
                    // Buttons
                    VStack(spacing: 12) {
                        Button(action: { viewModel.updateDetails(); dismiss() }) {
                            HStack {
                                Image(systemName: "archivebox")
                                Text("Update Details")
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppColors.primary)
                            .foregroundColor(.white)
                            .cornerRadius(12)
                        }
                        
                        Button(action: { viewModel.deleteItem(); dismiss() }) {
                            HStack {
                                Image(systemName: "trash")
                                Text("Delete Item")
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppColors.surface)
                            .foregroundColor(.red)
                            .cornerRadius(12)
                            .overlay(
                                RoundedRectangle(cornerRadius: 12)
                                    .stroke(Color.red.opacity(0.3), lineWidth: 1)
                            )
                        }
                    }
                    .padding(.horizontal, 24)
                    
                    Spacer().frame(height: 40)
                }
                .padding(.vertical)
            }
        }
        .background(AppColors.background)
        .navigationBarHidden(true)
    }
}

#Preview {
    UpdateItemPage(viewModel: UpdateItemViewModel(item: MockPantryRepository().getInventoryItems().first!))
}

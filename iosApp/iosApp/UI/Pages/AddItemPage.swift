import SwiftUI

struct AddItemPage: View {
    @Environment(\.presentationMode) var presentationMode
    @StateObject private var viewModel = AddItemViewModel()
    
    var body: some View {
        ZStack {
            NavigationView {
                ScrollView {
                    VStack(alignment: .leading, spacing: 24) {
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Add New Item")
                                .font(.system(size: 24, weight: .bold))
                            Text("Log your groceries to track freshness.")
                                .font(.system(size: 14))
                                .foregroundColor(.gray)
                        }
                        
                        // Barcode Scanner Placeholder
                        Button(action: {}) {
                            HStack {
                                Image(systemName: "qrcode.viewfinder")
                                Text("SCAN BARCODE")
                                    .fontWeight(.bold)
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 80)
                            .background(AppColors.background)
                            .foregroundColor(AppColors.primary)
                            .cornerRadius(12)
                            .overlay(
                                RoundedRectangle(cornerRadius: 12)
                                    .stroke(Color.gray.opacity(0.3), lineWidth: 1)
                            )
                        }
                        
                        Text("OR ENTER MANUALLY")
                            .font(.system(size: 12))
                            .foregroundColor(.gray)
                            .frame(maxWidth: .infinity, alignment: .center)
                        
                        // Form Fields
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Item Name")
                                .fontWeight(.medium)
                            TextField("e.g., Organic Honeycrisp Apples", text: $viewModel.itemName)
                                .padding()
                                .background(AppColors.surface)
                                .cornerRadius(12)
                                .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Category")
                                .fontWeight(.medium)
                            HStack {
                                ForEach(viewModel.categories, id: \.self) { category in
                                    CategoryChipView(
                                        text: category,
                                        isSelected: viewModel.selectedCategory == category,
                                        action: { viewModel.selectedCategory = category }
                                    )
                                }
                            }
                        }
                        
                        HStack(spacing: 16) {
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Quantity")
                                    .fontWeight(.medium)
                                TextField("", text: $viewModel.quantity)
                                    .padding()
                                    .background(AppColors.surface)
                                    .cornerRadius(12)
                                    .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
                            }
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Unit")
                                    .fontWeight(.medium)
                                HStack {
                                    Text(viewModel.unit)
                                    Spacer()
                                    Image(systemName: "chevron.down")
                                }
                                .padding()
                                .background(AppColors.surface)
                                .cornerRadius(12)
                                .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
                            }
                        }
                        
                        // Freshness Tracking
                        VStack(alignment: .leading, spacing: 12) {
                            HStack {
                                Image(systemName: "calendar")
                                    .foregroundColor(AppColors.secondary)
                                Text("Freshness Tracking")
                                    .fontWeight(.bold)
                            }
                            
                            HStack(spacing: 16) {
                                VStack(alignment: .leading) {
                                    Text("Purchase Date").font(.system(size: 12)).foregroundColor(.gray)
                                    DatePicker("", selection: $viewModel.purchaseDate, displayedComponents: .date)
                                        .labelsHidden()
                                }
                                .frame(maxWidth: .infinity, alignment: .leading)
                                
                                VStack(alignment: .leading) {
                                    Text("Expiry Date").font(.system(size: 12)).foregroundColor(.gray)
                                    DatePicker("", selection: $viewModel.expiryDate, displayedComponents: .date)
                                        .labelsHidden()
                                }
                                .frame(maxWidth: .infinity, alignment: .leading)
                            }
                        }
                        .padding()
                        .background(AppColors.warningContainer.opacity(0.5))
                        .cornerRadius(12)
                        
                        AppButton(title: viewModel.isLoading ? "Saving..." : "Save to Pantry", icon: "archivebox") {
                            viewModel.saveItem()
                        }
                        .disabled(viewModel.isLoading)
                        .padding(.top, 8)
                    }
                    .padding()
                }
                .background(AppColors.background)
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .navigationBarLeading) {
                        Button("Cancel") {
                            presentationMode.wrappedValue.dismiss()
                        }
                    }
                }
            }
            
            // Snackbar / Toast Overlay
            if let toast = viewModel.toast {
                VStack {
                    Spacer()
                    ToastView(toast: toast)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                }
                .animation(.spring(), value: viewModel.toast != nil)
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                        viewModel.toast = nil
                        if toast.type == .success {
                            presentationMode.wrappedValue.dismiss()
                        }
                    }
                }
            }
        }
    }
}

#Preview {
    AddItemPage()
}

struct CategoryChipView: View {
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

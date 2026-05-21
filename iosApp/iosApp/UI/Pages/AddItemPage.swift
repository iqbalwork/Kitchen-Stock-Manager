import SwiftUI

struct AddItemPage: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var itemName = ""
    @State private var quantity = "1"
    @State private var unit = "Pieces"
    @State private var selectedCategory = "Produce"
    @State private var purchaseDate = Date()
    @State private var expiryDate = Date()
    
    let categories = ["Produce", "Dairy", "Meat"]
    
    var body: some View {
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
                        .background(Color(hex: 0xF8F9FA))
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
                        .frame(maxWidth: .infinity)
                    
                    // Form Fields
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Item Name")
                            .fontWeight(.medium)
                        TextField("e.g., Organic Honeycrisp Apples", text: $itemName)
                            .padding()
                            .background(AppColors.surface)
                            .cornerRadius(12)
                            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
                    }
                    
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Category")
                            .fontWeight(.medium)
                        HStack {
                            ForEach(categories, id: \.self) { category in
                                CategoryChipView(
                                    text: category,
                                    isSelected: selectedCategory == category,
                                    action: { selectedCategory = category }
                                )
                            }
                        }
                    }
                    
                    HStack(spacing: 16) {
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Quantity")
                                .fontWeight(.medium)
                            TextField("", text: $quantity)
                                .padding()
                                .background(AppColors.surface)
                                .cornerRadius(12)
                                .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Unit")
                                .fontWeight(.medium)
                            HStack {
                                Text(unit)
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
                            DatePicker("Purchase", selection: $purchaseDate, displayedComponents: .date)
                                .labelsHidden()
                                .frame(maxWidth: .infinity)
                            
                            DatePicker("Expiry", selection: $expiryDate, displayedComponents: .date)
                                .labelsHidden()
                                .frame(maxWidth: .infinity)
                        }
                    }
                    .padding()
                    .background(Color(hex: 0xFFF9E6))
                    .cornerRadius(12)
                    
                    AppButton(title: "Save to Pantry", icon: "archivebox") {
                        presentationMode.wrappedValue.dismiss()
                    }
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

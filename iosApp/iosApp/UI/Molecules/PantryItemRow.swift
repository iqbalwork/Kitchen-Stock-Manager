import SwiftUI
import SharedLogic

struct PantryItemRow: View {
    let item: PantryItem
    var isShoppingMode: Bool = false
    var onCheckChanged: ((Bool) -> Void)? = nil
    var onEditClicked: (() -> Void)? = nil
    
    var body: some View {
        HStack(spacing: 12) {
            if isShoppingMode {
                Button(action: {
                    onCheckChanged?(!item.isChecked)
                }) {
                    Image(systemName: item.isChecked ? "checkmark.square.fill" : "square")
                        .foregroundColor(item.isChecked ? AppColors.primary : .gray)
                        .font(.system(size: 20))
                }
            } else {
                // Icon or Placeholder
                ZStack {
                    RoundedRectangle(cornerRadius: 8)
                        .fill(Color(hex: 0xF0F0F0))
                        .frame(width: 60, height: 60)
                    
                    Text(String(item.name.prefix(1)))
                        .font(.system(size: 24, weight: .bold))
                        .foregroundColor(.gray)
                }
            }
            
            VStack(alignment: .leading, spacing: 4) {
                HStack {
                    Text(item.name)
                        .font(.system(size: 16, weight: .bold))
                        .strikethrough(isShoppingMode && item.isChecked)
                    
                    Spacer()
                    
                    if let price = item.price {
                        Text(price)
                            .font(.system(size: 14, weight: .bold))
                    } else if !isShoppingMode {
                        StatusBadge(text: item.category.name.capitalized, color: .gray)
                    }
                }
                
                if let progress = item.progress {
                    Text("Remaining")
                        .font(.system(size: 12))
                        .foregroundColor(.gray)
                    
                    HStack {
                        GeometryReader { geometry in
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 3)
                                    .fill(Color(hex: 0xEEEEEE))
                                    .frame(height: 6)
                                
                                RoundedRectangle(cornerRadius: 3)
                                    .fill(statusColor)
                                    .frame(width: geometry.size.width * CGFloat(progress.floatValue), height: 6)
                            }
                        }
                        .frame(height: 6)
                        
                        Text(item.quantity)
                            .font(.system(size: 12))
                            .foregroundColor(statusColor)
                    }
                } else {
                    Text("Quantity")
                        .font(.system(size: 12))
                        .foregroundColor(.gray)
                    Text(item.quantity)
                        .font(.system(size: 14, weight: .medium))
                    
                    HStack(spacing: 4) {
                        Circle()
                            .fill(statusColor)
                            .frame(width: 8, height: 8)
                        Text(statusText)
                            .font(.system(size: 12))
                            .foregroundColor(statusColor)
                    }
                }
            }
            
            if isShoppingMode && item.price == nil {
                Button(action: {
                    onEditClicked?()
                }) {
                    Image(systemName: "pencil")
                        .foregroundColor(.gray)
                        .font(.system(size: 18))
                }
            }
        }
        .padding(12)
        .background(AppColors.surface)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.05), radius: 2, x: 0, y: 2)
    }
    
    private var statusColor: Color {
        // SKIE will map sealed classes to Swift enums or similar structures
        // For now using a simple mapping based on what I expect from SKIE
        if item.status is StockStatus.LowStock {
            return AppColors.lowStock
        } else if item.status is StockStatus.ExpiringSoon {
            return AppColors.expiringSoon
        } else if item.status is StockStatus.UseToday {
            return AppColors.tertiary
        } else {
            return AppColors.primary
        }
    }
    
    private var statusText: String {
        if let lowStock = item.status as? StockStatus.LowStock {
            return "\(lowStock.percentage)% left"
        } else if item.status is StockStatus.ExpiringSoon {
            return "Expiring Soon"
        } else if item.status is StockStatus.UseToday {
            return "Use Today"
        } else {
            return "Fresh"
        }
    }
}

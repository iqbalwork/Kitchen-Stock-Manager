import SwiftUI

struct MainTabView: View {
    @State private var selectedTab = 0
    @State private var showAddItem = false
    
    var body: some View {
        ZStack(alignment: .bottom) {
            TabView(selection: $selectedTab) {
                NavigationView {
                    DashboardPage()
                }
                .tabItem {
                    Label("Dashboard", systemImage: "square.grid.2x2.fill")
                }
                .tag(0)
                
                NavigationView {
                    InventoryPage()
                }
                .tabItem {
                    Label("Inventory", systemImage: "archivebox.fill")
                }
                .tag(1)
                
                NavigationView {
                    ShoppingPage()
                }
                .tabItem {
                    Label("Shopping", systemImage: "cart.fill")
                }
                .tag(2)
            }
            .accentColor(AppColors.primary)
            
            // Custom FAB
            Button(action: {
                showAddItem = true
            }) {
                ZStack {
                    Circle()
                        .fill(AppColors.primary)
                        .frame(width: 56, height: 56)
                        .shadow(color: AppColors.primary.opacity(0.3), radius: 4, x: 0, y: 4)
                    
                    Image(systemName: "plus")
                        .font(.system(size: 24, weight: .bold))
                        .foregroundColor(.white)
                }
            }
            .padding(.bottom, 60) // Position above TabBar
            .padding(.trailing, 20)
            .frame(maxWidth: .infinity, alignment: .trailing)
        }
        .sheet(isPresented: $showAddItem) {
            AddItemPage()
        }
    }
}

#Preview {
    MainTabView()
}

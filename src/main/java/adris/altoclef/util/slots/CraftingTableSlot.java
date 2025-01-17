
package adris.altoclef.util.slots;

public class CraftingTableSlot extends Slot {
    public CraftingTableSlot(int windowSlot) {
        this(windowSlot, false);
    }
    protected CraftingTableSlot(int slot, boolean inventory) {
        super(slot, inventory);
    }

    @Override
    public int inventorySlotToWindowSlot(int inventorySlot) {
        if (inventorySlot < 9) {
            return inventorySlot + 37;
        }
        return inventorySlot + 1;
    }

    @Override
    protected int windowSlotToInventorySlot(int windowSlot) {
        if (windowSlot >= 37) {
            return windowSlot - 37;
        }
        return windowSlot - 1;
    }

    @Override
    protected String getName() {
        return "CraftingTable";
    }


    public static CraftingTableSlot getInputSlot(int x, int y) {
        return getInputSlot(y * 3 + x, true);
    }
    public static CraftingTableSlot getInputSlot(int index, boolean big) {
        index += 1;
        if (big) {
            // Default
            return new CraftingTableSlot(index);
        } else {
            // Small recipe in big window
            int x = index % 2;
            int y = index / 2;
            return getInputSlot(x, y);
        }
    }

    public static final CraftingTableSlot OUTPUT_SLOT = new CraftingTableSlot(0);
}

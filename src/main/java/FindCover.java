import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.Tag;
import net.querz.nbt.tag.LongTag;

import java.io.IOException;

public class FindCover {
    public static void main(String[] args) throws IOException {
        NamedTag namedTag = NBTUtil.read("refinedstorage_nodes.dat");
        CompoundTag refinedstorage_nodes = (CompoundTag) namedTag.getTag();
        String nodeType = "refinedstorage:" + args[0];
        System.out.println("DataVersion: " + refinedstorage_nodes.getInt("DataVersion"));
        System.out.println("\n" + "Searching for: " + nodeType + "\n");
        CompoundTag data = (CompoundTag) refinedstorage_nodes.get("data");
        ListTag<CompoundTag> dl = data.getListTag("Nodes").asCompoundTagList();

        for (CompoundTag c : dl) {
            if (c.getString("Id").equals(nodeType)) {
                LongTag cablePos = c.getLongTag("Pos");
                CompoundTag cableData = c.getCompoundTag("Data");
                CompoundTag cover = cableData.getCompoundTag("Cover");
                if (cover != null && cover.size() > 0) {
                    for (Tag<?> cover_val : cover.values()) {
                        CompoundTag cv = (CompoundTag) cover_val;
                        IntTag coverItem = cv.getIntTag("Direction");
                        System.out.println("Node Pos: " + cablePos.valueToString() + "\n" + "Cover Direction: " + coverItem.valueToString() + "\n");
                        //System.out.println(cablePos.valueToString());
                    }
                }
            }
        }
    }
}

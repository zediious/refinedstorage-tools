import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;

import java.io.IOException;

public class PullCovers {
    public static void main(String[] args) throws IOException {
        NamedTag namedTag = NBTUtil.read("refinedstorage_nodes.dat");
        CompoundTag refinedstorage_nodes = (CompoundTag) namedTag.getTag();
        System.out.println("DataVersion: " + refinedstorage_nodes.getInt("DataVersion"));
        CompoundTag data = (CompoundTag) refinedstorage_nodes.get("data");
        ListTag<CompoundTag> dl = data.getListTag("Nodes").asCompoundTagList();

        for (CompoundTag c : dl) {
            CompoundTag cableData = c.getCompoundTag("Data");
            cableData.remove("Cover");
        }
        NBTUtil.write(namedTag, "refinedstorage_nodes_no_covers.dat");
    }
}
package club.zediious.rftools;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.Tag;
import net.querz.nbt.tag.LongTag;

import java.io.IOException;
import java.util.Scanner;

public class Tool {

    public static void findCovers() {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Please type what type of node you you wish to search for (i.e cable, importer)");
        String nodeInput = userInput.nextLine();
        String nodeType = "refinedstorage:" + nodeInput;
        NamedTag namedTag = null;

        try {

            namedTag = NBTUtil.read("refinedstorage_nodes.dat");

        } catch (Exception e) {

            System.out.println("\nYou must place \"refinedstorage_nodes.dat\" in the same directory as the binary\n\n" +
                    e + "\n");
            System.exit(1);

        }
        assert namedTag != null;
        CompoundTag refinedstorage_nodes = (CompoundTag) namedTag.getTag();
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
                        System.out.println("Node Pos: " + cablePos.valueToString() + "\n" +
                                "Cover Direction: " + coverItem.valueToString() + "\n");

                    }

                }

            }

        }

    }

    public static void pullCovers() throws IOException {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Please type what type of node you wish to remove covers from (i.e cable, importer)");
        String nodeInput = userInput.nextLine();
        String nodeType = "refinedstorage:" + nodeInput;
        NamedTag namedTag = null;

        try {

            namedTag = NBTUtil.read("refinedstorage_nodes.dat");

        } catch (Exception e) {

            System.out.println("\nYou must place \"refinedstorage_nodes.dat\" in the same directory as the binary\n\n" +
                    e + "\n");
            System.exit(1);

        }
        assert namedTag != null;
        CompoundTag refinedstorage_nodes = (CompoundTag) namedTag.getTag();
        System.out.println("DataVersion: " + refinedstorage_nodes.getInt("DataVersion"));
        CompoundTag data = (CompoundTag) refinedstorage_nodes.get("data");
        ListTag<CompoundTag> dl = data.getListTag("Nodes").asCompoundTagList();

        for (CompoundTag c : dl) {
            if (c.getString("Id").equals(nodeType)) {

                CompoundTag cableData = c.getCompoundTag("Data");
                cableData.remove("Cover");

            }

        }

        NBTUtil.write(namedTag, "refinedstorage_nodes_no_covers.dat");

    }

}
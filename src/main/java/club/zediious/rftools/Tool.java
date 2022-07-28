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

    // Tools called from "idle()" method by user

    public static void findCovers() {

        NamedTag namedTag = findNodesFile();
        System.out.println("Please type what type of node you you wish to search for (i.e cable, importer)");
        String nodeType = getNodeFromUser();

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

        NamedTag namedTag = findNodesFile();
        System.out.println("Please type what type of node you wish to remove covers from (i.e cable, importer)");
        String nodeType = getNodeFromUser();

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

        System.out.println("\nWriting to new file \"refinedstorage_nodes_no_covers.dat\"\n");
        NBTUtil.write(namedTag, "refinedstorage_nodes_no_covers.dat");

    }

    // Utility methods, used within above tools

    static String getNodeFromUser() {

        Scanner userInput = new Scanner(System.in);
        String nodeInput = userInput.nextLine();
        return "refinedstorage:" + nodeInput;

    }

    static NamedTag findNodesFile() {

        NamedTag namedTag = null;

        try {

            namedTag = NBTUtil.read("refinedstorage_nodes.dat");

        } catch (IOException e) {

            System.out.println("\nYou must place \"refinedstorage_nodes.dat\" in the same directory as the binary\n\n" + e + "\n");
            System.exit(1);

        }

        return namedTag;

    }

}

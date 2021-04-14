package com.dumbdogdiner.warrior.nms;

import com.dumbdogdiner.warrior.nms.networking.Protocol;
import lombok.Getter;

/**
 * Packet Type class containing each Packet
 * categorized by Protocol + Sender
 *
 * (code generator made by spazzylemons)
 */
@SuppressWarnings("unused")
public class PacketType {

    /**
     * Packets sent via the HANDSHAKE Protocol
     */
    public static class Handshake {

        public static class Client {

            public static final PacketType HANDSHAKE = new PacketType(Protocol.HANDSHAKING, 0, "PacketHandshakingInSetProtocol");

        }

    }

    /**
     * Packets sent via the STATUS Protocol
     */
    public static class Status {

        public static class Server {

            public static final PacketType RESPONSE = new PacketType(Protocol.STATUS, 0, "PacketStatusOutServerInfo");

            public static final PacketType PONG = new PacketType(Protocol.STATUS, 1, "PacketStatusOutPong");

        }

        public static class Client {

            public static final PacketType REQUEST = new PacketType(Protocol.STATUS, 0, "PacketStatusInStart");

            public static final PacketType PING = new PacketType(Protocol.STATUS, 1, "PacketStatusInPing");

        }

    }

    /**
     * Packets sent via the LOGIN Protocol
     */
    public static class Login {

        public static class Server {

            public static final PacketType DISCONNECT_LOGIN = new PacketType(Protocol.LOGIN, 0, "PacketLoginOutDisconnect");

            public static final PacketType ENCRYPTION_REQUEST = new PacketType(Protocol.LOGIN, 1, "PacketLoginOutEncryptionBegin");

            public static final PacketType LOGIN_SUCCESS = new PacketType(Protocol.LOGIN, 2, "PacketLoginOutSuccess");

            public static final PacketType SET_COMPRESSION = new PacketType(Protocol.LOGIN, 3, "PacketLoginOutSetCompression");

            public static final PacketType LOGIN_PLUGIN_REQUEST = new PacketType(Protocol.LOGIN, 4, "PacketLoginOutCustomPayload");

        }

        public static class Client {

            public static final PacketType LOGIN_START = new PacketType(Protocol.LOGIN, 0, "PacketLoginInStart");

            public static final PacketType ENCRYPTION_RESPONSE = new PacketType(Protocol.LOGIN, 1, "PacketLoginInEncryptionBegin");

            public static final PacketType LOGIN_PLUGIN_RESPONSE = new PacketType(Protocol.LOGIN, 2, "PacketLoginInCustomPayload");

        }

    }

    /**
     * Packets sent via the PLAY Protocol
     */
    public static class Play {

        /**
         * PLAY Packets sent by the Server
         */
        public static class Server {

            public static final PacketType SPAWN_ENTITY = new PacketType(Protocol.PLAY, 0, "PacketPlayOutSpawnEntity");

            public static final PacketType SPAWN_EXPERIENCE_ORB = new PacketType(Protocol.PLAY, 1, "PacketPlayOutSpawnEntityExperienceOrb");

            public static final PacketType SPAWN_LIVING_ENTITY = new PacketType(Protocol.PLAY, 2, "PacketPlayOutSpawnEntityLiving");

            public static final PacketType SPAWN_PAINTING = new PacketType(Protocol.PLAY, 3, "PacketPlayOutSpawnEntityPainting");

            public static final PacketType SPAWN_PLAYER = new PacketType(Protocol.PLAY, 4, "PacketPlayOutNamedEntitySpawn");

            public static final PacketType ENTITY_ANIMATION_CLIENTBOUND = new PacketType(Protocol.PLAY, 5, "PacketPlayOutAnimation");

            public static final PacketType STATISTICS = new PacketType(Protocol.PLAY, 6, "PacketPlayOutStatistic");

            public static final PacketType ACKNOWLEDGE_PLAYER_DIGGING = new PacketType(Protocol.PLAY, 7, "PacketPlayOutBlockBreak");

            public static final PacketType BLOCK_BREAK_ANIMATION = new PacketType(Protocol.PLAY, 8, "PacketPlayOutBlockBreakAnimation");

            public static final PacketType BLOCK_ENTITY_DATA = new PacketType(Protocol.PLAY, 9, "PacketPlayOutTileEntityData");

            public static final PacketType BLOCK_ACTION = new PacketType(Protocol.PLAY, 10, "PacketPlayOutBlockAction");

            public static final PacketType BLOCK_CHANGE = new PacketType(Protocol.PLAY, 11, "PacketPlayOutBlockChange");

            public static final PacketType BOSS_BAR = new PacketType(Protocol.PLAY, 12, "PacketPlayOutBoss");

            public static final PacketType SERVER_DIFFICULTY = new PacketType(Protocol.PLAY, 13, "PacketPlayOutServerDifficulty");

            public static final PacketType CHAT_MESSAGE_CLIENTBOUND = new PacketType(Protocol.PLAY, 14, "PacketPlayOutChat");

            public static final PacketType TAB_COMPLETE_CLIENTBOUND = new PacketType(Protocol.PLAY, 15, "PacketPlayOutTabComplete");

            public static final PacketType DECLARE_COMMANDS = new PacketType(Protocol.PLAY, 16, "PacketPlayOutCommands");

            public static final PacketType WINDOW_CONFIRMATION_CLIENTBOUND = new PacketType(Protocol.PLAY, 17, "PacketPlayOutTransaction");

            public static final PacketType CLOSE_WINDOW_CLIENTBOUND = new PacketType(Protocol.PLAY, 18, "PacketPlayOutCloseWindow");

            public static final PacketType WINDOW_ITEMS = new PacketType(Protocol.PLAY, 19, "PacketPlayOutWindowItems");

            public static final PacketType WINDOW_PROPERTY = new PacketType(Protocol.PLAY, 20, "PacketPlayOutWindowData");

            public static final PacketType SET_SLOT = new PacketType(Protocol.PLAY, 21, "PacketPlayOutSetSlot");

            public static final PacketType SET_COOLDOWN = new PacketType(Protocol.PLAY, 22, "PacketPlayOutSetCooldown");

            public static final PacketType PLUGIN_MESSAGE_CLIENTBOUND = new PacketType(Protocol.PLAY, 23, "PacketPlayOutCustomPayload");

            public static final PacketType NAMED_SOUND_EFFECT = new PacketType(Protocol.PLAY, 24, "PacketPlayOutCustomSoundEffect");

            public static final PacketType DISCONNECT_PLAY = new PacketType(Protocol.PLAY, 25, "PacketPlayOutKickDisconnect");

            public static final PacketType ENTITY_STATUS = new PacketType(Protocol.PLAY, 26, "PacketPlayOutEntityStatus");

            public static final PacketType EXPLOSION = new PacketType(Protocol.PLAY, 27, "PacketPlayOutExplosion");

            public static final PacketType UNLOAD_CHUNK = new PacketType(Protocol.PLAY, 28, "PacketPlayOutUnloadChunk");

            public static final PacketType CHANGE_GAME_STATE = new PacketType(Protocol.PLAY, 29, "PacketPlayOutGameStateChange");

            public static final PacketType OPEN_HORSE_WINDOW = new PacketType(Protocol.PLAY, 30, "PacketPlayOutOpenWindowHorse");

            public static final PacketType KEEP_ALIVE_CLIENTBOUND = new PacketType(Protocol.PLAY, 31, "PacketPlayOutKeepAlive");

            public static final PacketType CHUNK_DATA = new PacketType(Protocol.PLAY, 32, "PacketPlayOutMapChunk");

            public static final PacketType EFFECT = new PacketType(Protocol.PLAY, 33, "PacketPlayOutWorldEvent");

            public static final PacketType PARTICLE = new PacketType(Protocol.PLAY, 34, "PacketPlayOutWorldParticles");

            public static final PacketType UPDATE_LIGHT = new PacketType(Protocol.PLAY, 35, "PacketPlayOutLightUpdate");

            public static final PacketType JOIN_GAME = new PacketType(Protocol.PLAY, 36, "PacketPlayOutLogin");

            public static final PacketType MAP_DATA = new PacketType(Protocol.PLAY, 37, "PacketPlayOutMap");

            public static final PacketType TRADE_LIST = new PacketType(Protocol.PLAY, 38, "PacketPlayOutOpenWindowMerchant");

            public static final PacketType ENTITY_POSITION = new PacketType(Protocol.PLAY, 39, "PacketPlayOutRelEntityMove");

            public static final PacketType ENTITY_POSITION_AND_ROTATION = new PacketType(Protocol.PLAY, 40, "PacketPlayOutRelEntityMoveLook");

            public static final PacketType ENTITY_ROTATION = new PacketType(Protocol.PLAY, 41, "PacketPlayOutEntityLook");

            public static final PacketType ENTITY_MOVEMENT = new PacketType(Protocol.PLAY, 42, "PacketPlayOutEntity");

            public static final PacketType VEHICLE_MOVE_CLIENTBOUND = new PacketType(Protocol.PLAY, 43, "PacketPlayOutVehicleMove");

            public static final PacketType OPEN_BOOK = new PacketType(Protocol.PLAY, 44, "PacketPlayOutOpenBook");

            public static final PacketType OPEN_WINDOW = new PacketType(Protocol.PLAY, 45, "PacketPlayOutOpenWindow");

            public static final PacketType OPEN_SIGN_EDITOR = new PacketType(Protocol.PLAY, 46, "PacketPlayOutOpenSignEditor");

            public static final PacketType CRAFT_RECIPE_RESPONSE = new PacketType(Protocol.PLAY, 47, "PacketPlayOutAutoRecipe");

            public static final PacketType PLAYER_ABILITIES_CLIENTBOUND = new PacketType(Protocol.PLAY, 48, "PacketPlayOutAbilities");

            public static final PacketType COMBAT_EVENT = new PacketType(Protocol.PLAY, 49, "PacketPlayOutCombatEvent");

            public static final PacketType PLAYER_INFO = new PacketType(Protocol.PLAY, 50, "PacketPlayOutPlayerInfo");

            public static final PacketType FACE_PLAYER = new PacketType(Protocol.PLAY, 51, "PacketPlayOutLookAt");

            public static final PacketType PLAYER_POSITION_AND_LOOK_CLIENTBOUND = new PacketType(Protocol.PLAY, 52, "PacketPlayOutPosition");

            public static final PacketType UNLOCK_RECIPES = new PacketType(Protocol.PLAY, 53, "PacketPlayOutRecipes");

            public static final PacketType DESTROY_ENTITIES = new PacketType(Protocol.PLAY, 54, "PacketPlayOutEntityDestroy");

            public static final PacketType REMOVE_ENTITY_EFFECT = new PacketType(Protocol.PLAY, 55, "PacketPlayOutRemoveEntityEffect");

            public static final PacketType RESOURCE_PACK_SEND = new PacketType(Protocol.PLAY, 56, "PacketPlayOutResourcePackSend");

            public static final PacketType RESPAWN = new PacketType(Protocol.PLAY, 57, "PacketPlayOutRespawn");

            public static final PacketType ENTITY_HEAD_LOOK = new PacketType(Protocol.PLAY, 58, "PacketPlayOutEntityHeadRotation");

            public static final PacketType MULTI_BLOCK_CHANGE = new PacketType(Protocol.PLAY, 59, "PacketPlayOutMultiBlockChange");

            public static final PacketType SELECT_ADVANCEMENT_TAB = new PacketType(Protocol.PLAY, 60, "PacketPlayOutSelectAdvancementTab");

            public static final PacketType WORLD_BORDER = new PacketType(Protocol.PLAY, 61, "PacketPlayOutWorldBorder");

            public static final PacketType CAMERA = new PacketType(Protocol.PLAY, 62, "PacketPlayOutCamera");

            public static final PacketType HELD_ITEM_CHANGE_CLIENTBOUND = new PacketType(Protocol.PLAY, 63, "PacketPlayOutHeldItemSlot");

            public static final PacketType UPDATE_VIEW_POSITION = new PacketType(Protocol.PLAY, 64, "PacketPlayOutViewCentre");

            public static final PacketType UPDATE_VIEW_DISTANCE = new PacketType(Protocol.PLAY, 65, "PacketPlayOutViewDistance");

            public static final PacketType SPAWN_POSITION = new PacketType(Protocol.PLAY, 66, "PacketPlayOutSpawnPosition");

            public static final PacketType DISPLAY_SCOREBOARD = new PacketType(Protocol.PLAY, 67, "PacketPlayOutScoreboardDisplayObjective");

            public static final PacketType ENTITY_METADATA = new PacketType(Protocol.PLAY, 68, "PacketPlayOutEntityMetadata");

            public static final PacketType ATTACH_ENTITY = new PacketType(Protocol.PLAY, 69, "PacketPlayOutAttachEntity");

            public static final PacketType ENTITY_VELOCITY = new PacketType(Protocol.PLAY, 70, "PacketPlayOutEntityVelocity");

            public static final PacketType ENTITY_EQUIPMENT = new PacketType(Protocol.PLAY, 71, "PacketPlayOutEntityEquipment");

            public static final PacketType SET_EXPERIENCE = new PacketType(Protocol.PLAY, 72, "PacketPlayOutExperience");

            public static final PacketType UPDATE_HEALTH = new PacketType(Protocol.PLAY, 73, "PacketPlayOutUpdateHealth");

            public static final PacketType SCOREBOARD_OBJECTIVE = new PacketType(Protocol.PLAY, 74, "PacketPlayOutScoreboardObjective");

            public static final PacketType SET_PASSENGERS = new PacketType(Protocol.PLAY, 75, "PacketPlayOutMount");

            public static final PacketType TEAMS = new PacketType(Protocol.PLAY, 76, "PacketPlayOutScoreboardTeam");

            public static final PacketType UPDATE_SCORE = new PacketType(Protocol.PLAY, 77, "PacketPlayOutScoreboardScore");

            public static final PacketType TIME_UPDATE = new PacketType(Protocol.PLAY, 78, "PacketPlayOutUpdateTime");

            public static final PacketType TITLE = new PacketType(Protocol.PLAY, 79, "PacketPlayOutTitle");

            public static final PacketType ENTITY_SOUND_EFFECT = new PacketType(Protocol.PLAY, 80, "PacketPlayOutEntitySound");

            public static final PacketType SOUND_EFFECT = new PacketType(Protocol.PLAY, 81, "PacketPlayOutNamedSoundEffect");

            public static final PacketType STOP_SOUND = new PacketType(Protocol.PLAY, 82, "PacketPlayOutStopSound");

            public static final PacketType PLAYER_LIST_HEADER_AND_FOOTER = new PacketType(Protocol.PLAY, 83, "PacketPlayOutPlayerListHeaderFooter");

            public static final PacketType NBT_QUERY_RESPONSE = new PacketType(Protocol.PLAY, 84, "PacketPlayOutNBTQuery");

            public static final PacketType COLLECT_ITEM = new PacketType(Protocol.PLAY, 85, "PacketPlayOutCollect");

            public static final PacketType ENTITY_TELEPORT = new PacketType(Protocol.PLAY, 86, "PacketPlayOutEntityTeleport");

            public static final PacketType ADVANCEMENTS = new PacketType(Protocol.PLAY, 87, "PacketPlayOutAdvancements");

            public static final PacketType ENTITY_PROPERTIES = new PacketType(Protocol.PLAY, 88, "PacketPlayOutUpdateAttributes");

            public static final PacketType ENTITY_EFFECT = new PacketType(Protocol.PLAY, 89, "PacketPlayOutEntityEffect");

            public static final PacketType DECLARE_RECIPES = new PacketType(Protocol.PLAY, 90, "PacketPlayOutRecipeUpdate");

            public static final PacketType TAGS = new PacketType(Protocol.PLAY, 91, "PacketPlayOutTags");

        }

        /**
         * PLAY Packets sent by the Client
         */
        public static class Client {

            public static final PacketType TELEPORT_CONFIRM = new PacketType(Protocol.PLAY, 0, "PacketPlayInTeleportAccept");

            public static final PacketType QUERY_BLOCK_NBT = new PacketType(Protocol.PLAY, 1, "PacketPlayInTileNBTQuery");

            public static final PacketType SET_DIFFICULTY = new PacketType(Protocol.PLAY, 2, "PacketPlayInDifficultyChange");

            public static final PacketType CHAT_MESSAGE_SERVERBOUND = new PacketType(Protocol.PLAY, 3, "PacketPlayInChat");

            public static final PacketType CLIENT_STATUS = new PacketType(Protocol.PLAY, 4, "PacketPlayInClientCommand");

            public static final PacketType CLIENT_SETTINGS = new PacketType(Protocol.PLAY, 5, "PacketPlayInSettings");

            public static final PacketType TAB_COMPLETE_SERVERBOUND = new PacketType(Protocol.PLAY, 6, "PacketPlayInTabComplete");

            public static final PacketType WINDOW_CONFIRMATION_SERVERBOUND = new PacketType(Protocol.PLAY, 7, "PacketPlayInTransaction");

            public static final PacketType CLICK_WINDOW_BUTTON = new PacketType(Protocol.PLAY, 8, "PacketPlayInEnchantItem");

            public static final PacketType CLICK_WINDOW = new PacketType(Protocol.PLAY, 9, "PacketPlayInWindowClick");

            public static final PacketType CLOSE_WINDOW_SERVERBOUND = new PacketType(Protocol.PLAY, 10, "PacketPlayInCloseWindow");

            public static final PacketType PLUGIN_MESSAGE_SERVERBOUND = new PacketType(Protocol.PLAY, 11, "PacketPlayInCustomPayload");

            public static final PacketType EDIT_BOOK = new PacketType(Protocol.PLAY, 12, "PacketPlayInBEdit");

            public static final PacketType QUERY_ENTITY_NBT = new PacketType(Protocol.PLAY, 13, "PacketPlayInEntityNBTQuery");

            public static final PacketType INTERACT_ENTITY = new PacketType(Protocol.PLAY, 14, "PacketPlayInUseEntity");

            public static final PacketType GENERATE_STRUCTURE = new PacketType(Protocol.PLAY, 15, "PacketPlayInJigsawGenerate");

            public static final PacketType KEEP_ALIVE_SERVERBOUND = new PacketType(Protocol.PLAY, 16, "PacketPlayInKeepAlive");

            public static final PacketType LOCK_DIFFICULTY = new PacketType(Protocol.PLAY, 17, "PacketPlayInDifficultyLock");

            public static final PacketType PLAYER_POSITION = new PacketType(Protocol.PLAY, 18, "PacketPlayInPosition");

            public static final PacketType PLAYER_POSITION_AND_ROTATION_SERVERBOUND = new PacketType(Protocol.PLAY, 19, "PacketPlayInPositionLook");

            public static final PacketType PLAYER_ROTATION = new PacketType(Protocol.PLAY, 20, "PacketPlayInLook");

            public static final PacketType PLAYER_MOVEMENT = new PacketType(Protocol.PLAY, 21, "PacketPlayInFlying");

            public static final PacketType VEHICLE_MOVE_SERVERBOUND = new PacketType(Protocol.PLAY, 22, "PacketPlayInVehicleMove");

            public static final PacketType STEER_BOAT = new PacketType(Protocol.PLAY, 23, "PacketPlayInBoatMove");

            public static final PacketType PICK_ITEM = new PacketType(Protocol.PLAY, 24, "PacketPlayInPickItem");

            public static final PacketType CRAFT_RECIPE_REQUEST = new PacketType(Protocol.PLAY, 25, "PacketPlayInAutoRecipe");

            public static final PacketType PLAYER_ABILITIES_SERVERBOUND = new PacketType(Protocol.PLAY, 26, "PacketPlayInAbilities");

            public static final PacketType PLAYER_DIGGING = new PacketType(Protocol.PLAY, 27, "PacketPlayInBlockDig");

            public static final PacketType ENTITY_ACTION = new PacketType(Protocol.PLAY, 28, "PacketPlayInEntityAction");

            public static final PacketType STEER_VEHICLE = new PacketType(Protocol.PLAY, 29, "PacketPlayInSteerVehicle");

            public static final PacketType SET_RECIPE_BOOK_STATE = new PacketType(Protocol.PLAY, 30, "PacketPlayInRecipeSettings");

            public static final PacketType SET_DISPLAYED_RECIPE = new PacketType(Protocol.PLAY, 31, "PacketPlayInRecipeDisplayed");

            public static final PacketType NAME_ITEM = new PacketType(Protocol.PLAY, 32, "PacketPlayInItemName");

            public static final PacketType RESOURCE_PACK_STATUS = new PacketType(Protocol.PLAY, 33, "PacketPlayInResourcePackStatus");

            public static final PacketType ADVANCEMENT_TAB = new PacketType(Protocol.PLAY, 34, "PacketPlayInAdvancements");

            public static final PacketType SELECT_TRADE = new PacketType(Protocol.PLAY, 35, "PacketPlayInTrSel");

            public static final PacketType SET_BEACON_EFFECT = new PacketType(Protocol.PLAY, 36, "PacketPlayInBeacon");

            public static final PacketType HELD_ITEM_CHANGE_SERVERBOUND = new PacketType(Protocol.PLAY, 37, "PacketPlayInHeldItemSlot");

            public static final PacketType UPDATE_COMMAND_BLOCK = new PacketType(Protocol.PLAY, 38, "PacketPlayInSetCommandBlock");

            public static final PacketType UPDATE_COMMAND_BLOCK_MINECART = new PacketType(Protocol.PLAY, 39, "PacketPlayInSetCommandMinecart");

            public static final PacketType CREATIVE_INVENTORY_ACTION = new PacketType(Protocol.PLAY, 40, "PacketPlayInSetCreativeSlot");

            public static final PacketType UPDATE_JIGSAW_BLOCK = new PacketType(Protocol.PLAY, 41, "PacketPlayInSetJigsaw");

            public static final PacketType UPDATE_STRUCTURE_BLOCK = new PacketType(Protocol.PLAY, 42, "PacketPlayInStruct");

            public static final PacketType UPDATE_SIGN = new PacketType(Protocol.PLAY, 43, "PacketPlayInUpdateSign");

            public static final PacketType ANIMATION_SERVERBOUND = new PacketType(Protocol.PLAY, 44, "PacketPlayInArmAnimation");

            public static final PacketType SPECTATE = new PacketType(Protocol.PLAY, 45, "PacketPlayInSpectate");

            public static final PacketType PLAYER_BLOCK_PLACEMENT = new PacketType(Protocol.PLAY, 46, "PacketPlayInUseItem");

            public static final PacketType USE_ITEM = new PacketType(Protocol.PLAY, 47, "PacketPlayInBlockPlace");

        }

    }

    @Getter
    private final String name;

    @Getter
    private final int packetId;

    @Getter
    private final Protocol protocol;

    public PacketType(Protocol protocol, int packetId, String packetName) {
        this.name = packetName;
        this.packetId = packetId;
        this.protocol = protocol;
    }

}

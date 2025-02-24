### Description
This plugin allows you to set up a debug stick on your server so you can provide access to it for players in survival mode

### Setup
To give access to the debug stick in survival mode, you need to set permission **minecraft.debugstick.always** to **true**

### Features
Blacklist blocks  
List worlds prevent waterlogged  
Custom crafting recipe debug stick  

### Commands  
**/debugstick reload** - To reload plugin    
 
### Permissions  
 **debugstick.reload** - Permission to reload plugin (default: op)  
 **debugstick.bypass** - Bypassing plugin check (default: false)  
### Config
<details>
<summary>Config</summary>

```ini
# Disables checking for players in creative
# Отключает проверку игроков в креативе
Bypass_In_Creative: false

# Message when a player tries to interact with a block or state on the blacklist
# Сообщение когда игрок пытается взаимодействовать с блоком или состоянием блока из черного списка
Interaction_cancellation_messages:
  enable: true
  actionbar: true
  messageBlock: "You cannot interact with this block"
  messageState: "You cannot interact with this state"

# List of worlds where the player cannot create water using the debug stick
# Список миров, в которых игрок не может создать воду с помощью палочки отладки
Prevent_water_in_world:
  - "world_nether"
 #- "custom_world_name"

# Debug stick recipe config
# Конфиг рецепта палочки отладки
Debug_stick_craft:
  enabled: true

  # if grid is 2x2, line3 is ignored and the line length is 2 characters
  # если сетка 2x2, строка 3 игнорируется и длина строки составляет 2 символа
  grid_size: 2 # 3x3 => 3, 2x2 => 2, 1x1 => 1
  shape:
    line1: " N"  # craft grid | сетка крафта
    line2: "S "  # use space for empty slot
    line3: "   " # использовать space для пустого слота

  # ingredient key for craft grid
  # ключ ингредиента для сетки крафта
  ingredient:
    N: "NETHER_STAR"
    S: "STICK"

# List of blocks that the player will not be able to interact with
# Список блоков, с которыми игрок не сможет взаимодействовать
Blacklist_block:
  - bee_nest
  - beehive
  - cake
  - composter
  - respawn_anchor
  - end_portal_frame
  - turtle_egg
  - trial_spawner
  - vault
  - sculk_shrieker
  - sea_pickle
  - grass_block
  - water_cauldron

# List of block tags that the player will not be able to interact with
# Список тегов блоков, с которыми игрок не сможет взаимодействовать
Blacklist_tag_block:
  - candles
  - crops
  - doors
  - slabs
  - tall_flowers

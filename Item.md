# Item

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**verified** | **Boolean** | true if the item has not changed since it was linked. |  [optional]
**w** | [**BigDecimal**](BigDecimal.md) | Width of the item in inventory tiles. |  [optional]
**h** | [**BigDecimal**](BigDecimal.md) | Height of the item in inventory tiles. |  [optional]
**ilvl** | [**BigDecimal**](BigDecimal.md) | item level |  [optional]
**icon** | **String** | url to the image of the item |  [optional]
**league** | **String** | league identifier |  [optional]
**id** | [**UUID**](UUID.md) |  |  [optional]
**elder** | **Boolean** | true if the item can have elder mods (elder background). |  [optional]
**shaper** | **Boolean** | true if the item can have shaper mods (shaper background). |  [optional]
**sockets** | [**ItemSockets**](ItemSockets.md) |  |  [optional]
**name** | **String** | unique name of the item |  [optional]
**typeLine** | **String** | name of the baseitem (+prefix/suffix if existing) |  [optional]
**identified** | **Boolean** | true if the item is identified |  [optional]
**corrupted** | **Boolean** | true if the item is corrupted |  [optional]
**properties** | [**List&lt;ItemLineContent&gt;**](ItemLineContent.md) |  |  [optional]
**utilityMods** | **List&lt;String&gt;** | \\\&quot;Implicit\\\&quot; mods for flasks. |  [optional]
**explicitMods** | **List&lt;String&gt;** |  |  [optional]
**craftedMods** | **List&lt;String&gt;** | master crafted mods |  [optional]
**enchantMods** | **List&lt;String&gt;** | enchantments |  [optional]
**flavourText** | **List&lt;String&gt;** | Array of lines of the flavour text of e.g. unique items. |  [optional]
**descrText** | **String** | description |  [optional]
**secDescrText** | **String** | secondary description |  [optional]
**frameType** | [**FrameTypeEnum**](#FrameTypeEnum) | Frame \&quot;color\&quot; of the item depending on rarity, item type (e.g. gem) etc. Possible values with examples:   * &#x60;0&#x60; - normal items   * &#x60;1&#x60; - magic items   * &#x60;2&#x60; - rare items   * &#x60;3&#x60; - unique items   * &#x60;4&#x60; - gems  |  [optional]
**category** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) | Should only contain one property which is the \&quot;superclass\&quot; and an array where the only member is the \&quot;subclass\&quot;  |  [optional]
**x** | [**BigDecimal**](BigDecimal.md) | X coordinate in the specified frame. |  [optional]
**y** | [**BigDecimal**](BigDecimal.md) | Y coordinate in the specified frame. |  [optional]
**inventoryId** | **String** | Id of the slot where this item is located. &#x60;StashX&#x60; for stash number &#x60;X&#x60;. &#x60;BodyArmour&#x60;, &#x60;Flask&#x60; etc for actual inventory slots.  |  [optional]
**isRelic** | **Boolean** | true if the item is a relic item (introduced in Legacy league). |  [optional]
**socketetedItems** | [**List&lt;Item&gt;**](Item.md) | List of items that are socketed into the sockets of the item. The  schema has an additional property __socket__ which contains the  socket index.  |  [optional]
**socket** | [**BigDecimal**](BigDecimal.md) | Socket index in the parent item in which this item is socketed. |  [optional]
**colour** | [**ColourEnum**](#ColourEnum) | gem colour (originates from attribute requirement):   * &#x60;D&#x60; - green   * &#x60;I&#x60; - blue   * &#x60;S&#x60; - red    * &#x60;G&#x60; - white  |  [optional]

<a name="FrameTypeEnum"></a>
## Enum: FrameTypeEnum
Name | Value
---- | -----
_0 | &quot;0&quot;
_1 | &quot;1&quot;
_2 | &quot;2&quot;
_3 | &quot;3&quot;
_4 | &quot;4&quot;

<a name="ColourEnum"></a>
## Enum: ColourEnum
Name | Value
---- | -----
D | &quot;D&quot;
I | &quot;I&quot;
S | &quot;S&quot;
G | &quot;G&quot;

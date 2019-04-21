# ItemLineContent

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  |  [optional]
**values** | [**List&lt;List&lt;Object&gt;&gt;**](List.md) | Tuple where the first value is the display string. The second value describes how the value should be displayed:   * 0 - white (simple) text   * 1 - blue (augmented) text   * 4 - red (fire damage) text   * 5 - blue (cold damage) text   * 6 - yellow (lightning damage) text   * 7 - red-violet (chaos damage) text  |  [optional]
**displayMode** | [**DisplayModeEnum**](#DisplayModeEnum) | Possible values:   * 0 - &#x60;name&#x60; should be displayed as \\&#x60;${name}: ${values.join(&#x27;, &#x27;)}\\&#x60; if        values.length &gt; 0 otherwise just &#x27;${name}&#x27;   * 1 - &#x60;name&#x60; should be displayed as \\&#x60;${values[0]} ${name}\\&#x60;   * 2 - &#x60;name__ should be display as \\&#x60;${progressBar(values[0])} ${values[0]}\\&#x60;         i.e. &#x60;name&#x60; is not displayed   * 3 - &#x60;name&#x60; field contains placeholders for the values in           the format of &#x60;%\\d&#x60;. The number nth value in &#x60;values&#x60; (0-based)  |  [optional]
**type** | [**BigDecimal**](BigDecimal.md) | The order in which the lines should be displayed |  [optional]

<a name="DisplayModeEnum"></a>
## Enum: DisplayModeEnum
Name | Value
---- | -----
_0 | &quot;0&quot;
_1 | &quot;1&quot;
_2 | &quot;2&quot;
_3 | &quot;3&quot;

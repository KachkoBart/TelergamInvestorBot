package Enums.TinkoffEnums;

public enum ShareType {
    SHARE_TYPE_UNSPECIFIED,
    /**
     * <pre>
     *Обыкновенная
     * </pre>
     *
     * <code>SHARE_TYPE_COMMON = 1;</code>
     */
    SHARE_TYPE_COMMON,
    /**
     * <pre>
     *Привилегированная
     * </pre>
     *
     * <code>SHARE_TYPE_PREFERRED = 2;</code>
     */
    SHARE_TYPE_PREFERRED,
    /**
     * <pre>
     *Американские депозитарные расписки
     * </pre>
     *
     * <code>SHARE_TYPE_ADR = 3;</code>
     */
    SHARE_TYPE_ADR,
    /**
     * <pre>
     *Глобальные депозитарные расписки
     * </pre>
     *
     * <code>SHARE_TYPE_GDR = 4;</code>
     */
    SHARE_TYPE_GDR,
    /**
     * <pre>
     *Товарищество с ограниченной ответственностью
     * </pre>
     *
     * <code>SHARE_TYPE_MLP = 5;</code>
     */
    SHARE_TYPE_MLP,
    /**
     * <pre>
     *Акции из реестра Нью-Йорка
     * </pre>
     *
     * <code>SHARE_TYPE_NY_REG_SHRS = 6;</code>
     */
    SHARE_TYPE_NY_REG_SHRS,
    /**
     * <pre>
     *Закрытый инвестиционный фонд
     * </pre>
     *
     * <code>SHARE_TYPE_CLOSED_END_FUND = 7;</code>
     */
    SHARE_TYPE_CLOSED_END_FUND,
    /**
     * <pre>
     *Траст недвижимости
     * </pre>
     *
     * <code>SHARE_TYPE_REIT = 8;</code>
     */
    SHARE_TYPE_REIT;


    /**
     * <pre>
     *Значение не определено.
     * </pre>
     *
     * <code>SHARE_TYPE_UNSPECIFIED = 0;</code>
     */
    public static final String SHARE_TYPE_UNSPECIFIED_VALUE = "Значение не определено";
    /**
     * <pre>
     *Обыкновенная
     * </pre>
     *
     * <code>SHARE_TYPE_COMMON = 1;</code>
     */
    public static final String SHARE_TYPE_COMMON_VALUE = "Обыкновенная";
    /**
     * <pre>
     *Привилегированная
     * </pre>
     *
     * <code>SHARE_TYPE_PREFERRED = 2;</code>
     */
    public static final String SHARE_TYPE_PREFERRED_VALUE = "Привилегированная";
    /**
     * <pre>
     *Американские депозитарные расписки
     * </pre>
     *
     * <code>SHARE_TYPE_ADR = 3;</code>
     */
    public static final String SHARE_TYPE_ADR_VALUE = "Американские депозитарные расписки";
    /**
     * <pre>
     *Глобальные депозитарные расписки
     * </pre>
     *
     * <code>SHARE_TYPE_GDR = 4;</code>
     */
    public static final String SHARE_TYPE_GDR_VALUE = "Глобальные депозитарные расписки";
    /**
     * <pre>
     *Товарищество с ограниченной ответственностью
     * </pre>
     *
     * <code>SHARE_TYPE_MLP = 5;</code>
     */
    public static final String SHARE_TYPE_MLP_VALUE = "Товарищество с ограниченной ответственностью";
    /**
     * <pre>
     *Акции из реестра Нью-Йорка
     * </pre>
     *
     * <code>SHARE_TYPE_NY_REG_SHRS = 6;</code>
     */
    public static final String SHARE_TYPE_NY_REG_SHRS_VALUE = "Акции из реестра Нью-Йорка";
    /**
     * <pre>
     *Закрытый инвестиционный фонд
     * </pre>
     *
     * <code>SHARE_TYPE_CLOSED_END_FUND = 7;</code>
     */
    public static final String SHARE_TYPE_CLOSED_END_FUND_VALUE = "Закрытый инвестиционный фонд";
    /**
     * <pre>
     *Траст недвижимости
     * </pre>
     *
     * <code>SHARE_TYPE_REIT = 8;</code>
     */
    public static final String SHARE_TYPE_REIT_VALUE = "Траст недвижимости";

    public static String forNumber(int value) {
        switch (value) {
            case 0: return SHARE_TYPE_UNSPECIFIED_VALUE;
            case 1: return SHARE_TYPE_COMMON_VALUE;
            case 2: return SHARE_TYPE_PREFERRED_VALUE;
            case 3: return SHARE_TYPE_ADR_VALUE;
            case 4: return SHARE_TYPE_GDR_VALUE;
            case 5: return SHARE_TYPE_MLP_VALUE;
            case 6: return SHARE_TYPE_NY_REG_SHRS_VALUE;
            case 7: return SHARE_TYPE_CLOSED_END_FUND_VALUE;
            case 8: return SHARE_TYPE_REIT_VALUE;
            default: return null;
        }
    }
}

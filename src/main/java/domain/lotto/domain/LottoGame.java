package domain.lotto.domain;

import java.util.ArrayList;
import java.util.List;

public class LottoGame {
    private static final int LOTTO_PRICE = 1000;
    private final List<LottoLine> lottoLines;
    private final int gameCount;
    private LottoStatistics lottoStatistics;
    private WinnginLottoLine winningLottoLine;
    private LottoNumber bonusBall;
    private double profitRate;


    protected LottoGame(int buyingPrice) {
        this.lottoLines = new ArrayList<>();
        this.gameCount = calculateGameCount(buyingPrice);
        buyAutoLottoLines(gameCount);
    }

    protected LottoGame(int buyingPrice, int manualLottoCount, List<LottoLine> manualLottoLines) {
        this.lottoLines = new ArrayList<>();
        this.gameCount = calculateGameCount(buyingPrice);

        validateManualLottoCount(manualLottoCount);
        buyManualLottoLines(manualLottoLines);
        buyAutoLottoLines(gameCount - manualLottoCount);
    }

    private static int calculateGameCount(int buyingPrice) {
        return buyingPrice / LOTTO_PRICE;
    }

    public static LottoGame from(int buyingPrice) {
        return new LottoGame(buyingPrice);
    }

    public static LottoGame newInstance(int buyingPrice, int manualLottoCount, List<LottoLine> manualLottoLines) {
        return new LottoGame(buyingPrice, manualLottoCount, manualLottoLines);
    }

    private void validateManualLottoCount(int manualLottoCount) {
        if (manualLottoCount > gameCount) {
            throw new IllegalArgumentException("수동 구매 수는 전체 수보다 클 수 없습니다.");
        }
    }

    public void calculateStatistics() {
        this.lottoStatistics = winningLottoLine.match(lottoLines, bonusBall);
        this.profitRate = lottoStatistics.getTotalProfit() / ((double) gameCount * LOTTO_PRICE);
    }

    public void registerWinningLottoLine(WinnginLottoLine winningLottoLine) {
        this.winningLottoLine = winningLottoLine;
    }

    private void buyAutoLottoLines(int buyCount) {
        int index = 0;
        while (index < buyCount) {
            lottoLines.add(LottoLine.create());
            index++;
        }
    }

    private void buyManualLottoLines(List<LottoLine> manualLottoLines) {
        lottoLines.addAll(manualLottoLines);
    }


    public List<LottoLine> getLottoLines() {
        return lottoLines;
    }

    public int getGameCount() {
        return gameCount;
    }

    public LottoLine getWinningLottoLine() {
        return winningLottoLine;
    }

    public LottoStatistics getLottoStatistics() {
        return lottoStatistics;
    }

    public LottoNumber getBonusBall() {
        return bonusBall;
    }

    public double getProfitRate() {
        return profitRate;
    }

    public void registerBonusBall(int bonusBall) {
        this.bonusBall = LottoNumber.from(bonusBall);
    }
}

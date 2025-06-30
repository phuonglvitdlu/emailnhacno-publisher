package vab.com.vn.emailnhacno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.entity.KhachHangEntity;
import vab.com.vn.emailnhacno.entity.MailTemplate;
import vab.com.vn.emailnhacno.entity.NhacNoVayEntity;
import vab.com.vn.emailnhacno.repository.KhachHangRepository;
import vab.com.vn.emailnhacno.repository.NhacNoVayRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static vab.com.vn.emailnhacno.Helper.Helper.*;


@Service
public class TemplateService {

    @Autowired
    private KhachHangRepository khachHangRepo;
    @Autowired
    private NhacNoVayRepository nhacNoVayRepository;


    public String getTemplate(Object kh, Optional<MailTemplate> l_template, String component, int type, String runDate) {
        if (kh instanceof NhacNoVayEntity) {
            kh = (NhacNoVayEntity) kh;
            return getTemplateVay((NhacNoVayEntity) kh, l_template, component, type, runDate);
        } else {
            kh = (KhachHangEntity) kh;
            return getTemplateForTCKH((KhachHangEntity) kh, l_template, component, type, runDate);
        }

    }

    public String getTemplateVay(NhacNoVayEntity kh, Optional<MailTemplate> l_template, String component, int type, String runDate) {
        return getTemplateForVay(kh, l_template, component, runDate);
    }

    private String getTemplateForVay(NhacNoVayEntity kh, Optional<MailTemplate> l_template, String component, String runDate) {
        StringBuilder contentDongKH = new StringBuilder();
        StringBuilder contentSumKH = new StringBuilder();

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        List<NhacNoVayEntity> listNhacNoVay = nhacNoVayRepository.getDataByComponentAndCustomerNo(kh.getCOMPONENT(), kh.getCUSTOMER_NO(), runDate);

        BigDecimal sumDuNoGoc = BigDecimal.ZERO;
        BigDecimal sumSoTienVay = BigDecimal.ZERO;
        BigDecimal sumSoTienGocPhaiTra = BigDecimal.ZERO;
        BigDecimal sumSoTienLaiPhaiTra = BigDecimal.ZERO;
        BigDecimal sumTongSoTienPhaiTra = BigDecimal.ZERO;
        int index = 1;

        for (NhacNoVayEntity customer : listNhacNoVay) {
            BigDecimal DuNoGoc = customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO;
            BigDecimal gocPhaiTra = customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO;
            BigDecimal soTienVay = customer.getSO_TIEN_VAY() != null ? new BigDecimal(customer.getSO_TIEN_VAY().toString()) : BigDecimal.ZERO;
            BigDecimal laiPhaiTra = customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO;
            BigDecimal tongSoTienPhaiTra = gocPhaiTra.add(laiPhaiTra);

            sumDuNoGoc = sumDuNoGoc.add(DuNoGoc);
            sumSoTienVay = sumSoTienVay.add(soTienVay);
            sumSoTienGocPhaiTra = sumSoTienGocPhaiTra.add(gocPhaiTra);
            sumSoTienLaiPhaiTra = sumSoTienLaiPhaiTra.add(laiPhaiTra);
            sumTongSoTienPhaiTra = sumTongSoTienPhaiTra.add(tongSoTienPhaiTra);
            if (component.equals("VAY_QUA_HAN")) {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getTAIKHOAN_TRICH_NO() == null ? "" : customer.getTAIKHOAN_TRICH_NO()).append("</td>\n").append("</td>\n")
                        .append("    <td>").append(customer.getSO_NGAY_QUAHAN()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_QH_GOC() != null ? updateDate(formatDate(customer.getNGAY_QH_GOC()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_QH_LAI() != null ? updateDate(formatDate(customer.getNGAY_QH_LAI()), 0) : "").append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getSO_TIEN_VAY() != null ? new BigDecimal(customer.getSO_TIEN_VAY().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format((tongSoTienPhaiTra))).append("</td>\n")
                        .append("</tr>\n");

            } else {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getTAIKHOAN_TRICH_NO() == null ? "" : customer.getTAIKHOAN_TRICH_NO()).append("</td>\n")

                        .append("    <td>")
                        .append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "")
                        .append("</td>\n")
                        .append("    <td>")
                        .append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "")
                        .append("</td>\n")
                        .append("    <td>")
                        .append(customer.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN_THANHTOAN()), 0) : "")
                        .append("</td>\n")

                        .append("    <td>").append(currencyFormatter.format(customer.getSO_TIEN_VAY() != null ? new BigDecimal(customer.getSO_TIEN_VAY().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO)).append("</td>\n")
                        .append("    <td>")
                        .append(
                                currencyFormatter.format(
                                        (tongSoTienPhaiTra)
                                )
                        )
                        .append("</td>\n")
                        .append("</tr>\n");
            }
        }

        if (component.equals("VAY_QUA_HAN")) {
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"9\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienVay)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNoGoc)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienGocPhaiTra)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienLaiPhaiTra)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumTongSoTienPhaiTra)).append("</td>\n")
                    .append("</tr>");
        } else {
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"7\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienVay)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNoGoc)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienGocPhaiTra)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienLaiPhaiTra)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumTongSoTienPhaiTra)).append("</td>\n")
                    .append("</tr>");
        }
        if (component.equals("VAY_QUA_HAN")) {
            return l_template.get().getCONTENT()
                    .replace("#ho_ten@", kh.getTEN_KHACH_HANG())
                    .replace("#ma_kh@", kh.getCUSTOMER_NO())
                    .replace("#tong@", currencyFormatter.format(sumTongSoTienPhaiTra))
                    .replace("#ten_ch@", kh.getBRANCH_NAME())
                    .replace("#tk_tc@", kh.getACCOUNT_NUMBER())
                    .replace("#hmtc@", kh.getSO_HOP_DONG_VAY() == null ? "" : kh.getSO_HOP_DONG_VAY())
                    .replace("#contentDong@", contentDongKH.toString())
                    .replace("#contentSum@", contentSumKH.toString())
                    .replace("#hantruocngay@", kh.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN_THANHTOAN()), 0) : "")
                    .replace("#denngay@", kh.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN_THANHTOAN()), 0) : "")
                    .replace("#tungay@", kh.getNGAY_VAY() != null ? updateDate(formatDate(kh.getNGAY_VAY()), 0) : "")
                    .replace("#denngayHH@", kh.getNGAY_DEN_HAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN()), 0) : "")
                    .replace("#run_date@", kh.getRUN_DATE() != null ? updateDate(formatDate(kh.getRUN_DATE()), 0) : "");


        } else {
            return l_template.get().getCONTENT()
                    .replace("#ho_ten@", kh.getTEN_KHACH_HANG())
                    .replace("#ma_kh@", kh.getCUSTOMER_NO())
                    .replace("#tong@", currencyFormatter.format(sumTongSoTienPhaiTra))
                    .replace("#ten_ch@", kh.getBRANCH_NAME())
                    .replace("#tk_tc@", kh.getACCOUNT_NUMBER())
                    .replace("#hmtc@", kh.getSO_HOP_DONG_VAY() == null ? "" : kh.getSO_HOP_DONG_VAY())
                    .replace("#contentDong@", contentDongKH.toString())
                    .replace("#contentSum@", contentSumKH.toString())
                    .replace("#hantruocngay@", kh.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN_THANHTOAN()), 0) : "")
                    .replace("#denngay@", kh.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN_THANHTOAN()), 0) : "")
                    .replace("#tungay@", kh.getNGAY_VAY() != null ? updateDate(formatDate(kh.getNGAY_VAY()), 0) : "")
                    .replace("#denngayHH@", kh.getNGAY_DEN_HAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN()), 0) : "")
                    .replace("#run_date@", kh.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(kh.getNGAY_DEN_HAN_THANHTOAN()), 0) : "");

        }

    }

    public String getTemplateForVayCB(List<NhacNoVayEntity> listKHVay, Optional<MailTemplate> finalL_template, String component) {
        StringBuilder contentDongKH = new StringBuilder();
        StringBuilder contentSumKH = new StringBuilder();
//        DecimalFormat currencyFormatter = new DecimalFormat("#,###");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String runDate = "";
        BigDecimal sumDuNoGoc = BigDecimal.ZERO;
        BigDecimal sumSoTienVay = BigDecimal.ZERO;
        BigDecimal sumSoTienGocPhaiTra = BigDecimal.ZERO;
        BigDecimal sumSoTienLaiPhaiTra = BigDecimal.ZERO;
        BigDecimal sumTongSoTienPhaiTra = BigDecimal.ZERO;
        int index = 1;

        for (NhacNoVayEntity customer : listKHVay) {
            BigDecimal DuNoGoc = customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO;
            BigDecimal gocPhaiTra = customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO;
            BigDecimal soTienVay = customer.getSO_TIEN_VAY() != null ? new BigDecimal(customer.getSO_TIEN_VAY().toString()) : BigDecimal.ZERO;
            BigDecimal laiPhaiTra = customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO;
            BigDecimal tongSoTienPhaiTra = gocPhaiTra.add(laiPhaiTra);
            runDate = customer.getNGAY_DEN_HAN_THANHTOAN();
            sumDuNoGoc = sumDuNoGoc.add(DuNoGoc);
            sumSoTienVay = sumSoTienVay.add(soTienVay);
            sumSoTienGocPhaiTra = sumSoTienGocPhaiTra.add(gocPhaiTra);
            sumSoTienLaiPhaiTra = sumSoTienLaiPhaiTra.add(laiPhaiTra);
            sumTongSoTienPhaiTra = sumTongSoTienPhaiTra.add(tongSoTienPhaiTra);
            if (component.equals("VAY_QUA_HAN")) {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getTEN_KHACH_HANG() == null ? "" : customer.getTEN_KHACH_HANG()).append("</td>\n")
                        .append("    <td>").append(customer.getCUSTOMER_NO() == null ? "" : customer.getCUSTOMER_NO()).append("</td>\n")
                        .append("    <td>").append(customer.getRM_PHUTRACH() == null ? "" : customer.getRM_PHUTRACH()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getTAIKHOAN_TRICH_NO() == null ? "" : customer.getTAIKHOAN_TRICH_NO()).append("</td>\n").append("</td>\n")
                        .append("    <td>").append(customer.getSO_NGAY_QUAHAN()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_QH_GOC() != null ? updateDate(formatDate(customer.getNGAY_QH_GOC()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_QH_LAI() != null ? updateDate(formatDate(customer.getNGAY_QH_LAI()), 0) : "").append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getSO_TIEN_VAY() != null ? new BigDecimal(customer.getSO_TIEN_VAY().toString()) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("    <td>")
                        .append(currencyFormatter.format(tongSoTienPhaiTra).replace("₫", ""))
                        .append("</td>\n");

            } else if (component.equals("OD_QUA_HAN")) {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getTEN_KHACH_HANG() == null ? "" : customer.getTEN_KHACH_HANG()).append("</td>\n")
                        .append("    <td>").append(customer.getCUSTOMER_NO() == null ? "" : customer.getCUSTOMER_NO()).append("</td>\n")
                        .append("    <td>").append(customer.getRM_PHUTRACH() == null ? "" : customer.getRM_PHUTRACH()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN_THANHTOAN()), 0) : "").append("</td>\n")
                        .append("    <td>")
                        .append(
                                (customer.getSO_TIEN_VAY() != null
                                        ? currencyFormatter.format(new BigDecimal(customer.getSO_TIEN_VAY().toString().replace("₫", "").replace(",", "").trim()))
                                        : currencyFormatter.format(BigDecimal.ZERO)
                                ).replace("₫", "").trim()
                        )
                        .append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString().replace("₫", "")) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("</tr>\n");
            } else if (component.equals("OD_DEN_HAN") || component.equals("VAY_DEN_HAN")) {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getCUSTOMER_NO() == null ? "" : customer.getCUSTOMER_NO()).append("</td>\n")
                        .append("    <td>").append(customer.getTEN_KHACH_HANG() == null ? "" : customer.getTEN_KHACH_HANG()).append("</td>\n")
                        .append("    <td>").append(customer.getRM_PHUTRACH() == null ? "" : customer.getRM_PHUTRACH()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getSO_TIEN_VAY() != null ? currencyFormatter.format(new BigDecimal(customer.getSO_TIEN_VAY().toString())).replace("₫", "").trim() : currencyFormatter.format(BigDecimal.ZERO).replace("₫", "").trim()).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getDU_NO_GOC_HIENTAI() != null ? new BigDecimal(customer.getDU_NO_GOC_HIENTAI().toString()) : BigDecimal.ZERO).replace("₫", "").replace("₫", "").trim()).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getGOC_PHAI_TRA() != null ? new BigDecimal(customer.getGOC_PHAI_TRA().toString()) : BigDecimal.ZERO).replace("₫", "").replace("₫", "").trim()).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString()) : BigDecimal.ZERO).replace("₫", "").replace("₫", "").trim()).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getTONG_PHAI_TRA() != null ? new BigDecimal(customer.getTONG_PHAI_TRA().toString()) : BigDecimal.ZERO).replace("₫", "").replace("₫", "").trim()).append("</td>\n")
                        .append("</tr>\n");

            } else {
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>").append(customer.getSO_HOP_DONG_VAY() == null ? "" : customer.getSO_HOP_DONG_VAY()).append("</td>\n")
                        .append("    <td>").append(customer.getACCOUNT_NUMBER() == null ? "" : customer.getACCOUNT_NUMBER()).append("</td>\n")
                        .append("    <td>").append(customer.getCUSTOMER_NO() == null ? "" : customer.getCUSTOMER_NO()).append("</td>\n")
                        .append("    <td>").append(customer.getTEN_KHACH_HANG() == null ? "" : customer.getTEN_KHACH_HANG()).append("</td>\n")
                        .append("    <td>").append(customer.getRM_PHUTRACH() == null ? "" : customer.getRM_PHUTRACH()).append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_VAY() != null ? updateDate(formatDate(customer.getNGAY_VAY()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN()), 0) : "").append("</td>\n")
                        .append("    <td>").append(customer.getNGAY_DEN_HAN_THANHTOAN() != null ? updateDate(formatDate(customer.getNGAY_DEN_HAN_THANHTOAN()), 0) : "").append("</td>\n")
                        .append("    <td>")
                        .append(
                                (customer.getSO_TIEN_VAY() != null
                                        ? currencyFormatter.format(new BigDecimal(customer.getSO_TIEN_VAY().toString().replace("₫", "").replace(",", "").trim()))
                                        : currencyFormatter.format(BigDecimal.ZERO)
                                ).replace("₫", "").trim()
                        )
                        .append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getLAI_PHAI_TRA() != null ? new BigDecimal(customer.getLAI_PHAI_TRA().toString().replace("₫", "")) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getTONG_PHAI_TRA()!= null ? new BigDecimal(customer.getTONG_PHAI_TRA().toString().replace("₫", "")) : BigDecimal.ZERO).replace("₫", "")).append("</td>\n")

                        .append("</tr>\n");
            }
        }
        if (component.equals("VAY_QUA_HAN")) {
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"12\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienVay).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNoGoc).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienGocPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienLaiPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumTongSoTienPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("</tr>");
        } else if (component.equals("OD_HET_HAN")) {
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"11\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNoGoc).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienGocPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienLaiPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumTongSoTienPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("</tr>");

        } else {
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"9\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNoGoc).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienGocPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumSoTienLaiPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumTongSoTienPhaiTra).replace("₫", "")).append("</td>\n")
                    .append("</tr>");
        }


        return finalL_template.get().getCONTENT()
                .replace("#contentDong@", contentDongKH.toString())
                .replace("#contentSum@", contentSumKH.toString())
                .replace("#denngay@", runDate != null ? updateDate(formatDate(runDate), 9) : "")
                .replace("#hantruocngay@", runDate != null ? updateDate(formatDate(runDate), 9) : "")
                .replace("#run_date@", runDate != null ? updateDate(formatDate(runDate), 0) : "");

    }

    private String getTemplateForTCKH(KhachHangEntity kh, Optional<MailTemplate> l_template, String component, int type, String rpDate) {
        String resultTemp = "";

        String tenKH = kh.getCUSTOMER_NAME();
        String maKH = kh.getCUSTOMER_NO();
        String maHD = kh.getUSER_REF_NO();
        String soTK = kh.getACCOUNT_NUMBER();
        String tenCN = kh.getBRANCH_NAME();
        StringBuilder contentSumKH = new StringBuilder();
        String ngayCapHMTC = updateDate(formatDate(kh.getLIMIT_START_DATE()), 0);
        String ngayDHHMTC = updateDate(formatDate(kh.getLIMIT_EXPIRE_DATE()), 0);
        String ngayDHTT = updateDate(formatDate(kh.getSCHEDULE_DUE_DATE()), 0);
        String tuNgay = updateDate(formatDate(kh.getLIMIT_START_DATE()), 0);
        String runDate = updateDate(formatDate(kh.getSCHEDULE_DUE_DATE()), 0);

        StringBuilder contentDongKH = new StringBuilder();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        List<KhachHangEntity> listCustomersDue = khachHangRepo.getThauChiByCustomerno(Collections.singletonList(component), rpDate, kh.getCUSTOMER_NO());

        int index = 1;

        if (component.equals("OD_DEN_HAN")) {
            BigDecimal sumDuNo = BigDecimal.ZERO;
            BigDecimal sumLai = BigDecimal.ZERO;
            for (KhachHangEntity customer : listCustomersDue) {
                sumDuNo = sumDuNo.add(customer.getTOT_AMT());
                sumLai = sumLai.add(customer.getAMOUNT_DUE());
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>")
                        .append(customer.getUSER_REF_NO() != null ? customer.getUSER_REF_NO() : "")
                        .append("</td>\n")
                        .append("    <td>").append(ngayCapHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHTT).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getTOT_AMT())).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getAMOUNT_DUE())).append("</td>\n")
                        .append("</tr>\n");
            }
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"5\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNo)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumLai)).append("</td>\n")
                    .append("</tr>");
            resultTemp = l_template.get().getCONTENT()
                    .replace("#ho_ten@", tenKH)
                    .replace("#ma_kh@", maKH)
                    .replace("#ten_ch@", tenCN)
                    .replace("#tk_tc@", soTK)
                    .replace("#run_date@", runDate)
                    .replace("#denngay@", ngayDHTT)
                    .replace("#denngayHH@", ngayDHHMTC)
                    .replace("#hantruocngay@", ngayDHTT)
                    .replace("#runDate@", runDate)
                    .replace("#hmtc@", maHD == null ? "" : maHD)
                    .replace("#contentDong@", contentDongKH.toString())
                    .replace("#contentSum@", contentSumKH.toString());

        } else if (component.equals("OD_QUA_HAN")) {
            BigDecimal sumDuNo = BigDecimal.ZERO;
            BigDecimal sumLai = BigDecimal.ZERO;
            for (KhachHangEntity customer : listCustomersDue) {
                sumDuNo = sumDuNo.add(customer.getTOT_AMT());
                sumLai = sumLai.add(customer.getAMOUNT_DUE());
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>")
                        .append(customer.getUSER_REF_NO() != null ? customer.getUSER_REF_NO() : "")
                        .append("</td>\n")
                        .append("    <td>").append(ngayCapHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHTT).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getTOT_AMT())).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getAMOUNT_DUE())).append("</td>\n")
                        .append("</tr>\n");
            }
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"5\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNo)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumLai)).append("</td>\n")
                    .append("</tr>");
            resultTemp = l_template.get().getCONTENT()
                    .replace("#ho_ten@", tenKH)
                    .replace("#ma_kh@", maKH)
                    .replace("#ten_ch@", tenCN)
                    .replace("#tk_tc@", soTK)
                    .replace("#run_date@", runDate)
                    .replace("#denngay@", runDate != null ? updateDate(formatDate(runDate), 9) : "")
                    .replace("#denngayHH@", ngayDHHMTC)
                    .replace("#hantruocngay@", runDate != null ? updateDate(formatDate(runDate), 9) : "")
                    .replace("#runDate@", runDate)
                    .replace("#hmtc@", maHD == null ? "" : maHD)
                    .replace("#contentDong@", contentDongKH.toString())
                    .replace("#contentSum@", contentSumKH.toString());
        } else {
            BigDecimal sumDuNo = BigDecimal.ZERO;
            BigDecimal sumLai = BigDecimal.ZERO;
            for (KhachHangEntity customer : listCustomersDue) {
                sumDuNo = sumDuNo.add(customer.getTOT_AMT());
                sumLai = sumLai.add(customer.getAMOUNT_DUE());
                contentDongKH.append(" <tr>\n")
                        .append("    <td>").append(index++).append("</td>\n")
                        .append("    <td>")
                        .append(customer.getUSER_REF_NO() != null ? customer.getUSER_REF_NO() : "")
                        .append("</td>\n")
                        .append("    <td>").append(ngayCapHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHHMTC).append("</td>\n")
                        .append("    <td>").append(ngayDHTT).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getTOT_AMT())).append("</td>\n")
                        .append("    <td>").append(currencyFormatter.format(customer.getAMOUNT_DUE())).append("</td>\n")
                        .append("</tr>\n");
            }
            contentSumKH.append(" <tr>\n")
                    .append("    <td colspan=\"5\" style=\"text-align:right;\">Tổng cộng</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumDuNo)).append("</td>\n")
                    .append("    <td>").append(currencyFormatter.format(sumLai)).append("</td>\n")
                    .append("</tr>");
            resultTemp = l_template.get().getCONTENT()
                    .replace("#ho_ten@", tenKH)
                    .replace("#ma_kh@", maKH)
                    .replace("#ten_ch@", tenCN)
                    .replace("#tk_tc@", soTK)
                    .replace("#run_date@", runDate)
                    .replace("#tungay@", tuNgay != null ? updateDate(formatDate(tuNgay), 0) : "")
                    .replace("#denngay@", runDate != null ? updateDate(formatDate(runDate), 0) : "")
                    .replace("#denngayHH@", ngayDHHMTC)
                    .replace("#hantruocngay@", runDate != null ? updateDate(formatDate(runDate), 9) : "")
                    .replace("#runDate@", runDate)
                    .replace("#hmtc@", maHD == null ? "" : maHD)
                    .replace("#contentDong@", contentDongKH.toString())
                    .replace("#contentSum@", contentSumKH.toString());
        }
        return resultTemp;
    }


}

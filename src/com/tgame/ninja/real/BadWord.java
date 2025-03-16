package com.tgame.ninja.real;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class BadWord {

    public static String[] list = { "concac", "ditme", "cailin", "matday", "ditmay", "matnet", "damtac", "djtme", "dkm", "khonnan", "ocheo", "occho", "chode", "chochet", "dume", "duma", "dime", "memay", "mamay", "concac", "cailon", "mekiep", "deome", "bopvu", "bopveu", "bopchim", "lonto", "sucsinh", "quanque", "vailin", "vailon", "chetcha", "bulon", "bucac", "pulon", "pucac", "chetme", "chetba", "trungdai", "hondai", "lodit", "vaicalon", "vaicailon", "ditbo", "lonma", "lonme", "hiepdam", "bopcu", "bopvu", "bopcac", "laocho", "choma", "ditcu", "ditba", "dcm", "dmm", "dis", "vcl", "xaolon", "laolon", "soclo", "congsan", "shit", "lodit", "fuck", "concu", "hochiminh", "vonguyengiap", "nguyentandung", "truongtansang", "nguyenphutrong", "nguyensinhhung", "nguyenthidoan", "truonghoabinh", "nguyenhoabinh", "hoangtrunghai", "nguyenthiennhan", "nguyenxuanphuc", "vuvanninh", "phungquangthanh", "trandaiquang", "phambinhminh", "nguyenthaibinh", "hahungcuong", "buiquangvinh", "vuongdinhhue", "vuhuyhoang", "caoducphat", "dinhlathang", "trinhdinhdung", "nguyenminhquang", "nguyenbacson", "phamthihaichuyen", "hoangtuananh", "nguyenquan", "phamvuluan", "nguyenthikimtien", "giangseophu", "nguyenvanbinh", "huynhphongtranh", "vuducdam", "pussy", "lamtinh", "fuck", "shit", "cunt", "pussy", "dick", "asshole", "bullshit", "fucker", "vagina", "queer", "bastard", "nigger", "bitch", "tinhduc", "tjnhduc", "mại dâm", "mai dam" };

    public static String[] list2 = { "word", "*sc", ".sc", "vvap", "wáp", "dm", "dkm", "háck", "http", "iwin", "*mw", "fuck", "lon`", "l0n`", "lam tinh", ".mw", "www", "wap", ".com", ".vn", "sex", ",lt", ",yn", ",sh", ".lt", "hack", "*in", "dcm", "wáp", "tinhduc", ".yn", "hak", "*sh", "xtgem", ".sh", ".in", ".tk", ".net", "cặc", "lồn", ",in", ",sc", "*yn", "lt", "hochiminh" };

    public static String[] listvn = { "kặc", "cặc", "địt", "lồn", "lìn", "đụ", "dâm", "djt", "đéo", "vú", "vếu", "đít", "dái", "chó", "đỉ", "đĩ" };

    public static String[] extend = { ".com", ".info", ".name", ".net", ".org", ".pro", ".biz", ".asia", ".cat", ".coop", ".edu", ".gov", ".int", ".jobs", ".mil ", ".mobi", ".museum", ".tel", ".travel", ".ac", ".ad", ".ae", ".af", ".ag", ".ai", ".al", ".am", ".an", ".ao", ".aq", ".ar", ".as", ".at", ".au", ".aw", ".ax", ".az", ".ba", ".bb", ".bd", ".be", ".bf", ".bg", ".bh", ".bi", ".bj", ".bm", ".bn", ".bo", ".br", ".bs", ".bt", ".bw", ".by", ".bz", ".ca", ".cc", ".cd", ".cf", ".cg", ".ch", ".ci", ".ck", ".cl", ".cm", ".cn", ".co", ".cr", ".cu", ".cv", ".cx", ".cy", ".cz", ".de", ".dj", ".dk", ".dm", ".do", ".dz", ".ec", ".ee", ".eg", ".er", ".es", ".et", ".eu", ".fi", ".fj", ".fk", ".fm", ".fo", ".fr", ".ga", ".gd", ".ge", ".gf", ".gg", ".gh", ".gi", ".gl", ".gm", ".gn", ".gp", ".gq", ".gr", ".gs", ".gt", ".gu", ".gw", ".gy", ".hk", ".hm", ".hn", ".hr", ".ht", ".hu", ".id", ".ie", ".il", ".im", ".in", ".io", ".iq", ".ir", ".is", ".it", ".je", ".jm", ".jo", ".jp", ".ke", ".kg", ".kh", ".ki", ".km", ".kn", ".kp", ".kr", ".kw", ".ky", ".kz", ".la", ".lb", ".lc", ".li", ".lk", ".lr", ".ls", ".lt", ".lu", ".lv", ".ly", ".ma", ".mc", ".md", ".me", ".mg", ".mh", ".mk", ".ml", ".mm", ".mn", ".mo", ".mp", ".mq", ".mr", ".ms", ".mt", ".mu", ".mv", ".mw", ".mx", ".my", ".mz", ".na", ".nc", ".ne", ".nf", ".ng", ".ni", ".nl", ".no", ".np", ".nr", ".nu", ".nz", ".om", ".pa", ".pe", ".pf", ".pg", ".ph", ".pk", ".pl", ".pn", ".pr", ".ps", ".pt", ".pw", ".py", ".qa", ".re", ".ro", ".rs", ".ru", ".rw", ".sa", ".sb", ".sc", ".sd", ".se", ".sg", ".sh", ".si", ".sk", ".sl", ".sm", ".sn", ".sr", ".st", ".su", ".sv", ".sy", ".sz", ".tc", ".td", ".tf", ".tg", ".th", ".tj", ".tk", ".tl", ".tm", ".tn", ".to", ".tr", ".tt", ".tv", ".tw", ".tz", ".ua", ".ug", ".uk", ".us", ".uy", ".uz", ".va", ".vc", ".ve", ".vg", ".vi", ".vn", ".vu", ".wf", ".ws", ".ye", ".za", ".zm", ".zw", ".vn", ".com.vn", ".net.vn", ".biz.vn", ".info.vn", ".org.vn", ".gov.vn", ".name.vn", ".edu.vn", ".ac.vn", ".pro.vn", ".health.vn", ".angiang.vn", ".danang.vn", ".kontum.vn", ".quangtri.vn", ".bacgian.vn", ".dienbien.vn", ".laichau.vn", ".soctrang.vn", ".backan.vn", ".dongnai.vn", ".lamdong.vn", ".sonla.vn", ".baclieu.vn", ".dongthap.vn", ".langson.vn", ".tayninh.vn", ".bacninh.vn", ".gialai.vn", ".laocai.vn", ".thaibinh.vn.baria-vungtau.vn", ".hagiang.vn", ".longan.vn", ".thainguyen.vn", ".bentre.vn", ".haiduong.vn", ".namdinh.vn", ".thanhhoa.vn", ".binhdinh.vn", ".haiphong.vn", ".nghean.vn", ".thanhphohochiminh.vn", ".binhduong.vn", ".hanam.vn", ".ninhbinh.vn", ".thuathienhue.vn", ".binhphuoc.vn", ".hanoi.vn", ".ninhthuan.vn", ".tiengiang.vn", ".binhthuan.vn", ".hatinh.vn", ".phutho.vn", ".travinh.vn", ".camau.vn", ".haugiang.vn", ".phuyen.vn", ".tuyenquang.vn", ".cantho.vn", ".hoabinh.vn", ".quangbinh.vn", ".vinhlong.vn", ".caobang.vn", ".hungyen.vn", ".quangnam.vn", ".vinhphuc.vn", ".daklac.vn", ".khanhhoa.vn", ".quangngai.vn", ".yenbai.vn", ".daknong.vn", ".kiengiang.vn", ".quangninh.vn", ".Esy.es", ".16mb.com", ".96.Lt", ".hol.es", ".pe.hu", ".890m.com", ".wap.sh", "wap.", ".wap", "wap.", ".heck" };

    public static String replaceString(String info) {
        return info.replaceAll("[^a-zA-Z]", "");
    }

    public static String replaceString1(String info) {
        return info.replaceAll("[^a-zA-Z]", "");
    }

    public static String replaceStringWithSpace(String info) {
        return info.replaceAll("[^a-zA-Z0-9áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ@._+,: -]", "");
    }

    public static String replaceString3(String info) {
        return info.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String replaceSpaceString(String info) {
        return info.trim().replaceAll("\\s+", " ");
    }

    public static String checkBadWord2(String info) {
        info = replaceSpaceString(info.toLowerCase());
        for (String s : list2) {
            if (info.contains(s)) {
                info = info.replaceAll(s, "@");
            }
        }
        return info;
    }

    public static boolean ischeckBadWord2(String info) {
        info = replaceSpaceString(info.toLowerCase());
        for (String s : list2) {
            if (info.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBadWord(String info) {
        String infoVN = info;
        info = replaceString(removeAccent(info).toLowerCase()).trim();
        for (String value : list) {
            if (!value.equals("") && !value.equals(" ") && info.contains(value)) {
                return true;
            }
        }
        for (String s : listvn) {
            if (!s.equals("") && !s.equals(" ") && infoVN.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static String removeAccent(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        return s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String replaceExtendDomain(String info) {
        info = replaceSpaceString(info.toLowerCase());
        for (String s : extend) {
            if (info.contains(s.toLowerCase())) {
                info = info.replaceAll(s.toLowerCase(), "");
            }
        }
        return info;
    }
}

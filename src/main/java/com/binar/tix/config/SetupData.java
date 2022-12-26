package com.binar.tix.config;

import com.binar.tix.entities.*;
import com.binar.tix.payload.ScheduleAirplane;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.Constant;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SetupData {

    private final ClassSeats economiClass = new ClassSeats("Economi Class", 35000);
    private final ClassSeats businessClass = new ClassSeats("Business Class", 100000);

    private final ClassSeats firstClass = new ClassSeats("First Class", 1000000);

    private final Pricing economiPrice = new Pricing(600000);

    private final Pricing businessPrice = new Pricing(1200000);

    private final Pricing firstClassPrice = new Pricing(42000000);

    @Autowired
    InitializeService initService;

    public void generate() {
        DestinationCity jakarta = new DestinationCity("Jakarta");
        DestinationCity surabaya = new DestinationCity("Surabaya");
        DestinationCity medan = new DestinationCity("Medan");
        DestinationCity makassar = new DestinationCity("Makassar");
        DestinationCity yogyakarta = new DestinationCity("Yogyakarta");
        DestinationCity bali = new DestinationCity("Bali");
        DestinationCity palembang = new DestinationCity("Palembang");
        DestinationCity jeddah = new DestinationCity("Jeddah");
        DestinationCity london = new DestinationCity("London");
        DestinationCity tokyo = new DestinationCity("Tokyo");

        if (initService.getDestinationCity().isEmpty()) {
            //Destination City
            initService.initDestinationCity(jakarta);
            initService.initDestinationCity(surabaya);
            initService.initDestinationCity(medan);
            initService.initDestinationCity(makassar);
            initService.initDestinationCity(yogyakarta);
            initService.initDestinationCity(bali);
            initService.initDestinationCity(palembang);
            initService.initDestinationCity(jeddah);
            initService.initDestinationCity(london);
            initService.initDestinationCity(tokyo);
        }

        Airport airportJakarta = new Airport("Bandar Udara Internasional Soekarno–Hatta", "Kota Tangerang, Banten 19120", jakarta.getDestinationCityId());
        Airport airportSurabaya = new Airport("Bandar Udara Internasional Juanda", "Jl. Ir. H. Juanda, Betro, Kec. Sedati, Kabupaten Sidoarjo, Jawa Timur 61253", surabaya.getDestinationCityId());
        Airport airportMedan = new Airport("Bandar Udara Internasional Kualanamu", "Jl. Bandara Kuala Namu, Ps. Enam Kuala Namu, Kec. Beringin, Kabupaten Deli Serdang, Sumatera Utara 20553", medan.getDestinationCityId());
        Airport airportMakassar = new Airport("Bandar Udara Internasional Sultan Hasanuddin", "Jalan Airport No.1, Kec. Makassar, Kabupaten Maros, Sulawesi Selatan 90552", makassar.getDestinationCityId());
        Airport airportYogyakarta = new Airport("Bandar Udara Internasional Yogyakarta", "Kepek, Palihan, Kec. Temon, Kabupaten Kulon Progo, Daerah Istimewa Yogyakarta 55654", yogyakarta.getDestinationCityId());
        Airport airportBali = new Airport("Bandar Udara Internasional Ngurah Rai", "Jalan Raya Gusti Ngurah Rai, Tuban, Kec. Kuta, Kabupaten Badung, Bali 80362", bali.getDestinationCityId());
        Airport airportPalembang = new Airport("Bandar Udara Internasional Sultan Mahmud Badaruddin II", "Jl. Bandara Sultan Mahmud Badaruddin II, Talang Betutu, Kec. Sukarami, Kota Palembang, Sumatera Selatan 30761", palembang.getDestinationCityId());
        Airport airportJeddah = new Airport("King Abdulaziz International Airport", "Albwadi naer airport Arab Saudi", jeddah.getDestinationCityId());
        Airport airportTokyo = new Airport("Bandar Udara Internasional Tokyo", "Hanedakuko, Ota City, Tokyo 144-0041, Jepang", tokyo.getDestinationCityId());
        Airport airportLondon = new Airport("Bandar Udara Internasional London Heathrow", "Longford TW6, Inggris Raya", london.getDestinationCityId());

        if (initService.getAirport().isEmpty()) {
            //Airport
            initService.initAirport(airportJakarta);
            initService.initAirport(airportSurabaya);
            initService.initAirport(airportMedan);
            initService.initAirport(airportMakassar);
            initService.initAirport(airportYogyakarta);
            initService.initAirport(airportBali);
            initService.initAirport(airportPalembang);
            initService.initAirport(airportJeddah);
            initService.initAirport(airportLondon);
            initService.initAirport(airportTokyo);
        }

        //Passenge Type
        PassengerType type1 = new PassengerType("Anak - Anak", "Umur 2 - 11 Tahun");
        PassengerType type2 = new PassengerType("Dewasa", "12 Tahun lebih");
        PassengerType type3 = new PassengerType("Bayi", "Dibawah usia 2 tahun");

        if (initService.getPassengerType().isEmpty()) {
            initService.initPassengerType(type1);
            initService.initPassengerType(type2);
            initService.initPassengerType(type3);
        }

        //Citizenship
        if (initService.listCitizenship().isEmpty()) {
            String json = "[ \n" +
                    "  {name: 'Afghanistan', code: 'AF'}, \n" +
                    "  {name: 'Åland Islands', code: 'AX'}, \n" +
                    "  {name: 'Albania', code: 'AL'}, \n" +
                    "  {name: 'Algeria', code: 'DZ'}, \n" +
                    "  {name: 'American Samoa', code: 'AS'}, \n" +
                    "  {name: 'AndorrA', code: 'AD'}, \n" +
                    "  {name: 'Angola', code: 'AO'}, \n" +
                    "  {name: 'Anguilla', code: 'AI'}, \n" +
                    "  {name: 'Antarctica', code: 'AQ'}, \n" +
                    "  {name: 'Antigua and Barbuda', code: 'AG'}, \n" +
                    "  {name: 'Argentina', code: 'AR'}, \n" +
                    "  {name: 'Armenia', code: 'AM'}, \n" +
                    "  {name: 'Aruba', code: 'AW'}, \n" +
                    "  {name: 'Australia', code: 'AU'}, \n" +
                    "  {name: 'Austria', code: 'AT'}, \n" +
                    "  {name: 'Azerbaijan', code: 'AZ'}, \n" +
                    "  {name: 'Bahamas', code: 'BS'}, \n" +
                    "  {name: 'Bahrain', code: 'BH'}, \n" +
                    "  {name: 'Bangladesh', code: 'BD'}, \n" +
                    "  {name: 'Barbados', code: 'BB'}, \n" +
                    "  {name: 'Belarus', code: 'BY'}, \n" +
                    "  {name: 'Belgium', code: 'BE'}, \n" +
                    "  {name: 'Belize', code: 'BZ'}, \n" +
                    "  {name: 'Benin', code: 'BJ'}, \n" +
                    "  {name: 'Bermuda', code: 'BM'}, \n" +
                    "  {name: 'Bhutan', code: 'BT'}, \n" +
                    "  {name: 'Bolivia', code: 'BO'}, \n" +
                    "  {name: 'Bosnia and Herzegovina', code: 'BA'}, \n" +
                    "  {name: 'Botswana', code: 'BW'}, \n" +
                    "  {name: 'Bouvet Island', code: 'BV'}, \n" +
                    "  {name: 'Brazil', code: 'BR'}, \n" +
                    "  {name: 'British Indian Ocean Territory', code: 'IO'}, \n" +
                    "  {name: 'Brunei Darussalam', code: 'BN'}, \n" +
                    "  {name: 'Bulgaria', code: 'BG'}, \n" +
                    "  {name: 'Burkina Faso', code: 'BF'}, \n" +
                    "  {name: 'Burundi', code: 'BI'}, \n" +
                    "  {name: 'Cambodia', code: 'KH'}, \n" +
                    "  {name: 'Cameroon', code: 'CM'}, \n" +
                    "  {name: 'Canada', code: 'CA'}, \n" +
                    "  {name: 'Cape Verde', code: 'CV'}, \n" +
                    "  {name: 'Cayman Islands', code: 'KY'}, \n" +
                    "  {name: 'Central African Republic', code: 'CF'}, \n" +
                    "  {name: 'Chad', code: 'TD'}, \n" +
                    "  {name: 'Chile', code: 'CL'}, \n" +
                    "  {name: 'China', code: 'CN'}, \n" +
                    "  {name: 'Christmas Island', code: 'CX'}, \n" +
                    "  {name: 'Cocos (Keeling) Islands', code: 'CC'}, \n" +
                    "  {name: 'Colombia', code: 'CO'}, \n" +
                    "  {name: 'Comoros', code: 'KM'}, \n" +
                    "  {name: 'Congo', code: 'CG'}, \n" +
                    "  {name: 'Congo, The Democratic Republic of the', code: 'CD'}, \n" +
                    "  {name: 'Cook Islands', code: 'CK'}, \n" +
                    "  {name: 'Costa Rica', code: 'CR'}, \n" +
                    "  {name: 'Cote D\\'Ivoire', code: 'CI'}, \n" +
                    "  {name: 'Croatia', code: 'HR'}, \n" +
                    "  {name: 'Cuba', code: 'CU'}, \n" +
                    "  {name: 'Cyprus', code: 'CY'}, \n" +
                    "  {name: 'Czech Republic', code: 'CZ'}, \n" +
                    "  {name: 'Denmark', code: 'DK'}, \n" +
                    "  {name: 'Djibouti', code: 'DJ'}, \n" +
                    "  {name: 'Dominica', code: 'DM'}, \n" +
                    "  {name: 'Dominican Republic', code: 'DO'}, \n" +
                    "  {name: 'Ecuador', code: 'EC'}, \n" +
                    "  {name: 'Egypt', code: 'EG'}, \n" +
                    "  {name: 'El Salvador', code: 'SV'}, \n" +
                    "  {name: 'Equatorial Guinea', code: 'GQ'}, \n" +
                    "  {name: 'Eritrea', code: 'ER'}, \n" +
                    "  {name: 'Estonia', code: 'EE'}, \n" +
                    "  {name: 'Ethiopia', code: 'ET'}, \n" +
                    "  {name: 'Falkland Islands (Malvinas)', code: 'FK'}, \n" +
                    "  {name: 'Faroe Islands', code: 'FO'}, \n" +
                    "  {name: 'Fiji', code: 'FJ'}, \n" +
                    "  {name: 'Finland', code: 'FI'}, \n" +
                    "  {name: 'France', code: 'FR'}, \n" +
                    "  {name: 'French Guiana', code: 'GF'}, \n" +
                    "  {name: 'French Polynesia', code: 'PF'}, \n" +
                    "  {name: 'French Southern Territories', code: 'TF'}, \n" +
                    "  {name: 'Gabon', code: 'GA'}, \n" +
                    "  {name: 'Gambia', code: 'GM'}, \n" +
                    "  {name: 'Georgia', code: 'GE'}, \n" +
                    "  {name: 'Germany', code: 'DE'}, \n" +
                    "  {name: 'Ghana', code: 'GH'}, \n" +
                    "  {name: 'Gibraltar', code: 'GI'}, \n" +
                    "  {name: 'Greece', code: 'GR'}, \n" +
                    "  {name: 'Greenland', code: 'GL'}, \n" +
                    "  {name: 'Grenada', code: 'GD'}, \n" +
                    "  {name: 'Guadeloupe', code: 'GP'}, \n" +
                    "  {name: 'Guam', code: 'GU'}, \n" +
                    "  {name: 'Guatemala', code: 'GT'}, \n" +
                    "  {name: 'Guernsey', code: 'GG'}, \n" +
                    "  {name: 'Guinea', code: 'GN'}, \n" +
                    "  {name: 'Guinea-Bissau', code: 'GW'}, \n" +
                    "  {name: 'Guyana', code: 'GY'}, \n" +
                    "  {name: 'Haiti', code: 'HT'}, \n" +
                    "  {name: 'Heard Island and Mcdonald Islands', code: 'HM'}, \n" +
                    "  {name: 'Holy See (Vatican City State)', code: 'VA'}, \n" +
                    "  {name: 'Honduras', code: 'HN'}, \n" +
                    "  {name: 'Hong Kong', code: 'HK'}, \n" +
                    "  {name: 'Hungary', code: 'HU'}, \n" +
                    "  {name: 'Iceland', code: 'IS'}, \n" +
                    "  {name: 'India', code: 'IN'}, \n" +
                    "  {name: 'Indonesia', code: 'ID'}, \n" +
                    "  {name: 'Iran, Islamic Republic Of', code: 'IR'}, \n" +
                    "  {name: 'Iraq', code: 'IQ'}, \n" +
                    "  {name: 'Ireland', code: 'IE'}, \n" +
                    "  {name: 'Isle of Man', code: 'IM'}, \n" +
                    "  {name: 'Israel', code: 'IL'}, \n" +
                    "  {name: 'Italy', code: 'IT'}, \n" +
                    "  {name: 'Jamaica', code: 'JM'}, \n" +
                    "  {name: 'Japan', code: 'JP'}, \n" +
                    "  {name: 'Jersey', code: 'JE'}, \n" +
                    "  {name: 'Jordan', code: 'JO'}, \n" +
                    "  {name: 'Kazakhstan', code: 'KZ'}, \n" +
                    "  {name: 'Kenya', code: 'KE'}, \n" +
                    "  {name: 'Kiribati', code: 'KI'}, \n" +
                    "  {name: 'Korea, Democratic People\\'S Republic of', code: 'KP'}, \n" +
                    "  {name: 'Korea, Republic of', code: 'KR'}, \n" +
                    "  {name: 'Kuwait', code: 'KW'}, \n" +
                    "  {name: 'Kyrgyzstan', code: 'KG'}, \n" +
                    "  {name: 'Lao People\\'S Democratic Republic', code: 'LA'}, \n" +
                    "  {name: 'Latvia', code: 'LV'}, \n" +
                    "  {name: 'Lebanon', code: 'LB'}, \n" +
                    "  {name: 'Lesotho', code: 'LS'}, \n" +
                    "  {name: 'Liberia', code: 'LR'}, \n" +
                    "  {name: 'Libyan Arab Jamahiriya', code: 'LY'}, \n" +
                    "  {name: 'Liechtenstein', code: 'LI'}, \n" +
                    "  {name: 'Lithuania', code: 'LT'}, \n" +
                    "  {name: 'Luxembourg', code: 'LU'}, \n" +
                    "  {name: 'Macao', code: 'MO'}, \n" +
                    "  {name: 'Macedonia, The Former Yugoslav Republic of', code: 'MK'}, \n" +
                    "  {name: 'Madagascar', code: 'MG'}, \n" +
                    "  {name: 'Malawi', code: 'MW'}, \n" +
                    "  {name: 'Malaysia', code: 'MY'}, \n" +
                    "  {name: 'Maldives', code: 'MV'}, \n" +
                    "  {name: 'Mali', code: 'ML'}, \n" +
                    "  {name: 'Malta', code: 'MT'}, \n" +
                    "  {name: 'Marshall Islands', code: 'MH'}, \n" +
                    "  {name: 'Martinique', code: 'MQ'}, \n" +
                    "  {name: 'Mauritania', code: 'MR'}, \n" +
                    "  {name: 'Mauritius', code: 'MU'}, \n" +
                    "  {name: 'Mayotte', code: 'YT'}, \n" +
                    "  {name: 'Mexico', code: 'MX'}, \n" +
                    "  {name: 'Micronesia, Federated States of', code: 'FM'}, \n" +
                    "  {name: 'Moldova, Republic of', code: 'MD'}, \n" +
                    "  {name: 'Monaco', code: 'MC'}, \n" +
                    "  {name: 'Mongolia', code: 'MN'}, \n" +
                    "  {name: 'Montserrat', code: 'MS'}, \n" +
                    "  {name: 'Morocco', code: 'MA'}, \n" +
                    "  {name: 'Mozambique', code: 'MZ'}, \n" +
                    "  {name: 'Myanmar', code: 'MM'}, \n" +
                    "  {name: 'Namibia', code: 'NA'}, \n" +
                    "  {name: 'Nauru', code: 'NR'}, \n" +
                    "  {name: 'Nepal', code: 'NP'}, \n" +
                    "  {name: 'Netherlands', code: 'NL'}, \n" +
                    "  {name: 'Netherlands Antilles', code: 'AN'}, \n" +
                    "  {name: 'New Caledonia', code: 'NC'}, \n" +
                    "  {name: 'New Zealand', code: 'NZ'}, \n" +
                    "  {name: 'Nicaragua', code: 'NI'}, \n" +
                    "  {name: 'Niger', code: 'NE'}, \n" +
                    "  {name: 'Nigeria', code: 'NG'}, \n" +
                    "  {name: 'Niue', code: 'NU'}, \n" +
                    "  {name: 'Norfolk Island', code: 'NF'}, \n" +
                    "  {name: 'Northern Mariana Islands', code: 'MP'}, \n" +
                    "  {name: 'Norway', code: 'NO'}, \n" +
                    "  {name: 'Oman', code: 'OM'}, \n" +
                    "  {name: 'Pakistan', code: 'PK'}, \n" +
                    "  {name: 'Palau', code: 'PW'}, \n" +
                    "  {name: 'Palestinian Territory, Occupied', code: 'PS'}, \n" +
                    "  {name: 'Panama', code: 'PA'}, \n" +
                    "  {name: 'Papua New Guinea', code: 'PG'}, \n" +
                    "  {name: 'Paraguay', code: 'PY'}, \n" +
                    "  {name: 'Peru', code: 'PE'}, \n" +
                    "  {name: 'Philippines', code: 'PH'}, \n" +
                    "  {name: 'Pitcairn', code: 'PN'}, \n" +
                    "  {name: 'Poland', code: 'PL'}, \n" +
                    "  {name: 'Portugal', code: 'PT'}, \n" +
                    "  {name: 'Puerto Rico', code: 'PR'}, \n" +
                    "  {name: 'Qatar', code: 'QA'}, \n" +
                    "  {name: 'Reunion', code: 'RE'}, \n" +
                    "  {name: 'Romania', code: 'RO'}, \n" +
                    "  {name: 'Russian Federation', code: 'RU'}, \n" +
                    "  {name: 'RWANDA', code: 'RW'}, \n" +
                    "  {name: 'Saint Helena', code: 'SH'}, \n" +
                    "  {name: 'Saint Kitts and Nevis', code: 'KN'}, \n" +
                    "  {name: 'Saint Lucia', code: 'LC'}, \n" +
                    "  {name: 'Saint Pierre and Miquelon', code: 'PM'}, \n" +
                    "  {name: 'Saint Vincent and the Grenadines', code: 'VC'}, \n" +
                    "  {name: 'Samoa', code: 'WS'}, \n" +
                    "  {name: 'San Marino', code: 'SM'}, \n" +
                    "  {name: 'Sao Tome and Principe', code: 'ST'}, \n" +
                    "  {name: 'Saudi Arabia', code: 'SA'}, \n" +
                    "  {name: 'Senegal', code: 'SN'}, \n" +
                    "  {name: 'Serbia and Montenegro', code: 'CS'}, \n" +
                    "  {name: 'Seychelles', code: 'SC'}, \n" +
                    "  {name: 'Sierra Leone', code: 'SL'}, \n" +
                    "  {name: 'Singapore', code: 'SG'}, \n" +
                    "  {name: 'Slovakia', code: 'SK'}, \n" +
                    "  {name: 'Slovenia', code: 'SI'}, \n" +
                    "  {name: 'Solomon Islands', code: 'SB'}, \n" +
                    "  {name: 'Somalia', code: 'SO'}, \n" +
                    "  {name: 'South Africa', code: 'ZA'}, \n" +
                    "  {name: 'South Georgia and the South Sandwich Islands', code: 'GS'}, \n" +
                    "  {name: 'Spain', code: 'ES'}, \n" +
                    "  {name: 'Sri Lanka', code: 'LK'}, \n" +
                    "  {name: 'Sudan', code: 'SD'}, \n" +
                    "  {name: 'Suriname', code: 'SR'}, \n" +
                    "  {name: 'Svalbard and Jan Mayen', code: 'SJ'}, \n" +
                    "  {name: 'Swaziland', code: 'SZ'}, \n" +
                    "  {name: 'Sweden', code: 'SE'}, \n" +
                    "  {name: 'Switzerland', code: 'CH'}, \n" +
                    "  {name: 'Syrian Arab Republic', code: 'SY'}, \n" +
                    "  {name: 'Taiwan, Province of China', code: 'TW'}, \n" +
                    "  {name: 'Tajikistan', code: 'TJ'}, \n" +
                    "  {name: 'Tanzania, United Republic of', code: 'TZ'}, \n" +
                    "  {name: 'Thailand', code: 'TH'}, \n" +
                    "  {name: 'Timor-Leste', code: 'TL'}, \n" +
                    "  {name: 'Togo', code: 'TG'}, \n" +
                    "  {name: 'Tokelau', code: 'TK'}, \n" +
                    "  {name: 'Tonga', code: 'TO'}, \n" +
                    "  {name: 'Trinidad and Tobago', code: 'TT'}, \n" +
                    "  {name: 'Tunisia', code: 'TN'}, \n" +
                    "  {name: 'Turkey', code: 'TR'}, \n" +
                    "  {name: 'Turkmenistan', code: 'TM'}, \n" +
                    "  {name: 'Turks and Caicos Islands', code: 'TC'}, \n" +
                    "  {name: 'Tuvalu', code: 'TV'}, \n" +
                    "  {name: 'Uganda', code: 'UG'}, \n" +
                    "  {name: 'Ukraine', code: 'UA'}, \n" +
                    "  {name: 'United Arab Emirates', code: 'AE'}, \n" +
                    "  {name: 'United Kingdom', code: 'GB'}, \n" +
                    "  {name: 'United States', code: 'US'}, \n" +
                    "  {name: 'United States Minor Outlying Islands', code: 'UM'}, \n" +
                    "  {name: 'Uruguay', code: 'UY'}, \n" +
                    "  {name: 'Uzbekistan', code: 'UZ'}, \n" +
                    "  {name: 'Vanuatu', code: 'VU'}, \n" +
                    "  {name: 'Venezuela', code: 'VE'}, \n" +
                    "  {name: 'Viet Nam', code: 'VN'}, \n" +
                    "  {name: 'Virgin Islands, British', code: 'VG'}, \n" +
                    "  {name: 'Virgin Islands, U.S.', code: 'VI'}, \n" +
                    "  {name: 'Wallis and Futuna', code: 'WF'}, \n" +
                    "  {name: 'Western Sahara', code: 'EH'}, \n" +
                    "  {name: 'Yemen', code: 'YE'}, \n" +
                    "  {name: 'Zambia', code: 'ZM'}, \n" +
                    "  {name: 'Zimbabwe', code: 'ZW'} \n" +
                    "]";
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                initService.initCitizenship(new Citizenship(object.getString("name"), object.getString("code")));
            }
        }

        //Payment
        if (initService.listPayment().isEmpty()) {
            initService.initPayment(new Payment("Link Aja"));
            initService.initPayment(new Payment("DANA"));
            initService.initPayment(new Payment("Transfer Bank BCA"));
            initService.initPayment(new Payment("Transfer Bank Mandiri"));
            initService.initPayment(new Payment("Transfer Bank BRI"));
        }

        //Airplane
        Airplane jktFirstClass = new Airplane();
        Airplane jktEconomyBussines = new Airplane();
        Airplane jktEconomyBussines2 = new Airplane();
        Airplane sbyEconomyBussines = new Airplane();
        Airplane sbyEconomyBussines2 = new Airplane();
        Airplane mdnEconomyBussines = new Airplane();
        Airplane mdnEconomyBussines2 = new Airplane();
        Airplane mksEconomyBussines = new Airplane();
        Airplane mksEconomyBussines2 = new Airplane();
        Airplane ygEconomyBussines = new Airplane();
        Airplane ygEconomyBussines2 = new Airplane();
        Airplane baliEconomyBussines = new Airplane();
        Airplane baliEconomyBussines2 = new Airplane();
        Airplane plbEconomyBussines = new Airplane();
        Airplane plbEconomyBussines2 = new Airplane();
        Airplane jeddahFc = new Airplane();
        Airplane tokyoFc = new Airplane();
        Airplane londonFc = new Airplane();
        if (initService.getAirplane().isEmpty()) {
            String typeFirstClass = "Boeing 777-300ER";
            String typeA = "Boeing 737-800NG";
            String typeB = "Airbus A330-200";
            String typeC = "Airbus A330-300";
            String typeD = "Airbus A330-900neo";

            //Class Seats
            initService.initClassSeats(economiClass);
            initService.initClassSeats(businessClass);
            initService.initClassSeats(firstClass);

            //Pricing
            economiPrice.setClassId(economiClass.getClassId());
            businessPrice.setClassId(businessClass.getClassId());
            firstClassPrice.setClassId(firstClassPrice.getClassId());
            initService.initPricing(economiPrice);
            initService.initPricing(businessPrice);
            initService.initPricing(firstClassPrice);

            //Airplane - Jakarta
            jktFirstClass.setType(typeFirstClass);
            jktFirstClass.setLuggageCapacity(50);
            jktFirstClass.setAirportId(airportJakarta.getIdAirport());
            jktFirstClass.setSeats(generateSeatsFirstClass());
            initService.initAirplanes(jktFirstClass);

            jktEconomyBussines.setType(typeA);
            jktEconomyBussines.setLuggageCapacity(20);
            jktEconomyBussines.setAirportId(airportJakarta.getIdAirport());
            jktEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(jktEconomyBussines);

            jktEconomyBussines2.setType(typeB);
            jktEconomyBussines2.setLuggageCapacity(20);
            jktEconomyBussines2.setAirportId(airportJakarta.getIdAirport());
            jktEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(jktEconomyBussines2);

            //Airplane - Surabaya
            sbyEconomyBussines.setType(typeC);
            sbyEconomyBussines.setLuggageCapacity(20);
            sbyEconomyBussines.setAirportId(airportSurabaya.getIdAirport());
            sbyEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(sbyEconomyBussines);

            sbyEconomyBussines2.setType(typeD);
            sbyEconomyBussines2.setLuggageCapacity(20);
            sbyEconomyBussines2.setAirportId(airportSurabaya.getIdAirport());
            sbyEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(sbyEconomyBussines2);

            //Airplane - Medan
            mdnEconomyBussines.setType(typeA);
            mdnEconomyBussines.setLuggageCapacity(20);
            mdnEconomyBussines.setAirportId(airportMedan.getIdAirport());
            mdnEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mdnEconomyBussines);

            mdnEconomyBussines2.setType(typeB);
            mdnEconomyBussines2.setLuggageCapacity(20);
            mdnEconomyBussines2.setAirportId(airportMedan.getIdAirport());
            mdnEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mdnEconomyBussines2);

            //Airplane - Makassar
            mksEconomyBussines.setType(typeC);
            mksEconomyBussines.setLuggageCapacity(20);
            mksEconomyBussines.setAirportId(airportMakassar.getIdAirport());
            mksEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mksEconomyBussines);

            mksEconomyBussines2.setType(typeD);
            mksEconomyBussines2.setLuggageCapacity(20);
            mksEconomyBussines2.setAirportId(airportMakassar.getIdAirport());
            mksEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mksEconomyBussines2);

            //Airplane - Yogyakarta
            ygEconomyBussines.setType(typeA);
            ygEconomyBussines.setLuggageCapacity(20);
            ygEconomyBussines.setAirportId(airportYogyakarta.getIdAirport());
            ygEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(ygEconomyBussines);

            ygEconomyBussines2.setType(typeB);
            ygEconomyBussines2.setLuggageCapacity(20);
            ygEconomyBussines2.setAirportId(airportYogyakarta.getIdAirport());
            ygEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(ygEconomyBussines2);

            //Airplane - Bali
            baliEconomyBussines.setType(typeC);
            baliEconomyBussines.setLuggageCapacity(20);
            baliEconomyBussines.setAirportId(airportBali.getIdAirport());
            baliEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(baliEconomyBussines);

            baliEconomyBussines2.setType(typeD);
            baliEconomyBussines2.setLuggageCapacity(20);
            baliEconomyBussines2.setAirportId(airportBali.getIdAirport());
            baliEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(baliEconomyBussines2);

            //Airplane - Palembang
            plbEconomyBussines.setType(typeA);
            plbEconomyBussines.setLuggageCapacity(20);
            plbEconomyBussines.setAirportId(airportPalembang.getIdAirport());
            plbEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(plbEconomyBussines);

            plbEconomyBussines2.setType(typeB);
            plbEconomyBussines2.setLuggageCapacity(20);
            plbEconomyBussines2.setAirportId(airportPalembang.getIdAirport());
            plbEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(plbEconomyBussines2);

            //Airplane - Jeddah
            jeddahFc.setType(typeFirstClass);
            jeddahFc.setLuggageCapacity(50);
            jeddahFc.setAirportId(airportJeddah.getIdAirport());
            jeddahFc.setSeats(generateSeatsFirstClass());
            initService.initAirplanes(jeddahFc);

            //Airplane - Tokyo
            tokyoFc.setType(typeFirstClass);
            tokyoFc.setLuggageCapacity(50);
            tokyoFc.setAirportId(airportTokyo.getIdAirport());
            jeddahFc.setSeats(generateSeatsFirstClass());
            initService.initAirplanes(tokyoFc);

            //Airplane - London
            londonFc.setType(typeFirstClass);
            londonFc.setLuggageCapacity(50);
            londonFc.setAirportId(airportLondon.getIdAirport());
            jeddahFc.setSeats(generateSeatsFirstClass());
            initService.initAirplanes(londonFc);
        }

        //Facility Class
        if (initService.listFacility().isEmpty()) {
            List<Facility> facilities = Arrays.asList(new Facility("Dilengkapi dengan layar sentuh 9 inci untuk menikmati inflight entertaiment", economiClass.getClassId()), new Facility("Kursi standar yang nyaman", economiClass.getClassId()), new Facility("Ruang kaki dengan lebar 17 inci", economiClass.getClassId()), new Facility("Mendapatkan makanan dan minuman", economiClass.getClassId())
                    , new Facility("Tempat duduk berdesain ergonomis dengan ruang kaki selebar 106cm", businessClass.getClassId()), new Facility("Sajian hidangan makanan bervariasi seperti khas Eropa, Asia, Jepang dan Indonesia", businessClass.getClassId()), new Facility("Tersedia wine, champagne, bir dan minuman ringan lainnya", businessClass.getClassId()), new Facility("Dilengkapi dengan layar sentuh LCD berukuran 11 inci dengan AVOD di setiap kursi untuk menikmati inflight entertaiment", businessClass.getClassId()), new Facility("Terdapat power supply laptop di setiap kursi", businessClass.getClassId())
                    , new Facility("Memiliki asister first class yang akan mengurus semua hal", firstClass.getClassId()), new Facility("Kursi ergonomis dengan panjang 82 inci dan lebar 22 inci dan bisa menjadi tempat tidur datar lengkap dengan matras, selimut, bantal dan juga ottoman", firstClass.getClassId()), new Facility("Setiap suite dilengkapi dengan sliding door, seat control dengan panel layar sentuh dan pembatas bagi suite lini tengah", firstClass.getClassId()), new Facility("Inflight Entertaiment berupa layar LCD sebesar 23.5 inci dengan remote control dan headphone kedap suara", firstClass.getClassId()), new Facility("Lampu baca pribadi, alat tulis eksklusif, perlengkapan mandi branded dan sandal", firstClass.getClassId()), new Facility("Menu makanan Indonesia dan Internasional disiapkan oleh Chef on-board", firstClass.getClassId()));
            initService.initFacility(facilities);
        }

        if (initService.getDestination().isEmpty()) {
            //Destination
            //--Jakarta
            //Jakarta - Surabaya
            Destination djkt1 = new Destination(jakarta.getDestinationCityId(), surabaya.getDestinationCityId(), 305000, 90);
            //Jakarta - Medan
            Destination djkt3 = new Destination(jakarta.getDestinationCityId(), medan.getDestinationCityId(), 720000, 140);
            //Jakarta - Makassar
            Destination djkt5 = new Destination(jakarta.getDestinationCityId(), makassar.getDestinationCityId(), 640000, 145);
            //Jakarta - Yogyakarta
            Destination djkt7 = new Destination(jakarta.getDestinationCityId(), yogyakarta.getDestinationCityId(), 224800, 75);
            //Jakarta - Bali
            Destination djkt9 = new Destination(jakarta.getDestinationCityId(), bali.getDestinationCityId(), 472000, 110);
            //Jakarta - Palembang
            Destination djkt11 = new Destination(jakarta.getDestinationCityId(), palembang.getDestinationCityId(), 212800, 65);
            //Jakarta - Tokyo
            Destination djkt12 = new Destination(jakarta.getDestinationCityId(), tokyo.getDestinationCityId(), 21723750, 435);
            //Jakarta - Jeddah
            Destination djkt13 = new Destination(jakarta.getDestinationCityId(), jeddah.getDestinationCityId(), 29891250, 725);
            //Jakarta - London
            Destination djkt14 = new Destination(jakarta.getDestinationCityId(), london.getDestinationCityId(), 43927500, 900);

            List<Destination> destJktNegara = Arrays.asList(djkt12, djkt13, djkt14);
            initService.initDestination(destJktNegara);
            generateScheduleFirstClass(destJktNegara, jktFirstClass.getAirplaneId());

            List<Destination> destJkt = Arrays.asList(djkt1, djkt3, djkt5, djkt7, djkt9, djkt11);
            initService.initDestination(destJkt);
            generateSchedule(destJkt, jktEconomyBussines.getAirplaneId(), jktEconomyBussines2.getAirplaneId());

            //--Surabaya
            //Surabaya - Jakarta
            Destination sj1 = new Destination(surabaya.getDestinationCityId(), jakarta.getDestinationCityId(), 305000, 90);
            //Surabaya - Medan
            Destination sj3 = new Destination(surabaya.getDestinationCityId(), medan.getDestinationCityId(), 1062800, 255);
            //Surabaya - Makassar
            Destination sj5 = new Destination(surabaya.getDestinationCityId(), makassar.getDestinationCityId(), 329600, 90);
            // Surabaya - Yogyakarta
            Destination sj7 = new Destination(surabaya.getDestinationCityId(), yogyakarta.getDestinationCityId(), 129600, 175);
            //Surabaya - Bali
            Destination sj9 = new Destination(surabaya.getDestinationCityId(), bali.getDestinationCityId(), 169600, 55);
            //Surabaya - Palembang
            Destination sj11 = new Destination(surabaya.getDestinationCityId(), palembang.getDestinationCityId(), 521600, 150);

            List<Destination> destSby = Arrays.asList(sj1, sj3, sj5, sj7, sj9, sj11);
            initService.initDestination(destSby);
            generateSchedule(destSby, sbyEconomyBussines.getAirplaneId(), sbyEconomyBussines2.getAirplaneId());

            //--Medan
            //Medan - Jakarta
            Destination mj1 = new Destination(medan.getDestinationCityId(), jakarta.getDestinationCityId(), 752400, 140);
            //Medan - Surabaya
            Destination mj3 = new Destination(medan.getDestinationCityId(), surabaya.getDestinationCityId(), 1062800, 260);
            //Medan - Makassar
            Destination mj5 = new Destination(medan.getDestinationCityId(), makassar.getDestinationCityId(), 1391600, 355);
            //Medan - Yogyakarta
            Destination mj7 = new Destination(medan.getDestinationCityId(), yogyakarta.getDestinationCityId(), 974400, 165);
            //Medan - Bali
            Destination mj9 = new Destination(medan.getDestinationCityId(), bali.getDestinationCityId(), 1222000, 275);
            //Medan - Palembang
            Destination mj11 = new Destination(medan.getDestinationCityId(), palembang.getDestinationCityId(), 543600, 180);

            List<Destination> destMdn = Arrays.asList(mj1, mj3, mj5, mj7, mj9, mj11);
            initService.initDestination(destMdn);
            generateSchedule(destMdn, mdnEconomyBussines.getAirplaneId(), mdnEconomyBussines2.getAirplaneId());

            //--Makassar
            //Makassar - Jakarta
            Destination mkj1 = new Destination(makassar.getDestinationCityId(), jakarta.getDestinationCityId(), 640000, 140);
            //Makassar - Surabaya
            Destination mkj3 = new Destination(makassar.getDestinationCityId(), surabaya.getDestinationCityId(), 329600, 95);
            //Makassar - Medan
            Destination mkj5 = new Destination(makassar.getDestinationCityId(), medan.getDestinationCityId(), 1391600, 340);
            //Makassar - Yogyakarta
            Destination mkj7 = new Destination(makassar.getDestinationCityId(), yogyakarta.getDestinationCityId(), 458800, 120);
            //Makassar - Bali
            Destination mkj9 = new Destination(makassar.getDestinationCityId(), bali.getDestinationCityId(), 398400, 85);
            //Makassar - Palembang
            Destination mkj11 = new Destination(makassar.getDestinationCityId(), palembang.getDestinationCityId(), 850800, 285);

            List<Destination> destMks = Arrays.asList(mkj1, mkj3, mkj5, mkj7, mkj9, mkj11);
            initService.initDestination(destMks);
            generateSchedule(destMks, mksEconomyBussines.getAirplaneId(), mksEconomyBussines2.getAirplaneId());

            //--Yogyakarta
            //Yogyakarta - Jakarta
            Destination yj1 = new Destination(yogyakarta.getDestinationCityId(), jakarta.getDestinationCityId(), 224800, 70);
            //Yogyakarta - Surabaya
            Destination yj3 = new Destination(yogyakarta.getDestinationCityId(), surabaya.getDestinationCityId(), 129600, 210);
            //Yogyakarta - Medan
            Destination yj5 = new Destination(yogyakarta.getDestinationCityId(), medan.getDestinationCityId(), 974400, 165);
            //Yogyakarta - Makassar
            Destination yj7 = new Destination(yogyakarta.getDestinationCityId(), makassar.getDestinationCityId(), 458800, 120);
            //Yogyakarta - Bali
            Destination yj9 = new Destination(yogyakarta.getDestinationCityId(), bali.getDestinationCityId(), 289200, 140);
            //Yogyakarta - Palembang
            Destination yj11 = new Destination(yogyakarta.getDestinationCityId(), palembang.getDestinationCityId(), 433600, 165);

            List<Destination> destYg = Arrays.asList(yj1, yj3, yj5, yj7, yj9, yj11);
            initService.initDestination(destYg);
            generateSchedule(destYg, ygEconomyBussines.getAirplaneId(), ygEconomyBussines2.getAirplaneId());

            //--Bali
            //Bali - Jakarta
            Destination destBali1 = new Destination(bali.getDestinationCityId(), jakarta.getDestinationCityId(), 472000, 110);
            //Bali - Surabaya
            Destination destBali3 = new Destination(bali.getDestinationCityId(), surabaya.getDestinationCityId(), 169600, 55);
            //Bali - Medan
            Destination destBali5 = new Destination(bali.getDestinationCityId(), medan.getDestinationCityId(), 1222000, 275);
            //Bali - Makassar
            Destination destBali7 = new Destination(bali.getDestinationCityId(), makassar.getDestinationCityId(), 398400, 140);
            //Bali - Yogyakarta
            Destination destBali9 = new Destination(bali.getDestinationCityId(), yogyakarta.getDestinationCityId(), 289200, 145);
            //Bali - Palembang
            Destination destBali11 = new Destination(bali.getDestinationCityId(), palembang.getDestinationCityId(), 680800, 240);

            List<Destination> destBali = Arrays.asList(destBali1, destBali3, destBali5, destBali7, destBali9, destBali11);
            initService.initDestination(destBali);
            generateSchedule(destBali, baliEconomyBussines.getAirplaneId(), baliEconomyBussines2.getAirplaneId());

            //--Palembang
            //Palembang - Jakarta
            Destination destPlb1 = new Destination(palembang.getDestinationCityId(), jakarta.getDestinationCityId(), 212800, 65);
            //Palembang - Surabaya
            Destination destPlb3 = new Destination(palembang.getDestinationCityId(), surabaya.getDestinationCityId(), 521600, 150);
            //Palembang - Medan
            Destination destPlb5 = new Destination(palembang.getDestinationCityId(), medan.getDestinationCityId(), 543600, 180);
            //Palembang - Makassar
            Destination destPlb7 = new Destination(palembang.getDestinationCityId(), makassar.getDestinationCityId(), 850800, 285);
            //Palembang - Yogyakarta
            Destination destPlb9 = new Destination(palembang.getDestinationCityId(), yogyakarta.getDestinationCityId(), 433600, 165);
            //Palembang - Bali
            Destination destPlb11 = new Destination(palembang.getDestinationCityId(), bali.getDestinationCityId(), 680800, 240);

            List<Destination> destPlb = Arrays.asList(destPlb1, destPlb3, destPlb5, destPlb7, destPlb9, destPlb11);
            initService.initDestination(destPlb);
            generateSchedule(destPlb, plbEconomyBussines.getAirplaneId(), plbEconomyBussines2.getAirplaneId());

            //Tokyo - Jakarta
            Destination tokyoJakarta = new Destination(tokyo.getDestinationCityId(), jakarta.getDestinationCityId(), 21723750, 435);
            List<Destination> destExternal1 = List.of(tokyoJakarta);
            initService.initDestination(destExternal1);
            generateScheduleFirstClass(destExternal1, tokyoFc.getAirplaneId());
            //Jeddah - Jakarta
            Destination jeddahJakarta = new Destination(jeddah.getDestinationCityId(), jakarta.getDestinationCityId(), 29891250, 725);
            List<Destination> destExternal2 = List.of(jeddahJakarta);
            initService.initDestination(destExternal2);
            generateScheduleFirstClass(destExternal2, jeddahFc.getAirplaneId());
            //London - Jakarta
            Destination londonJakarta = new Destination(london.getDestinationCityId(), jakarta.getDestinationCityId(), 43927500, 900);
            List<Destination> destExternal3 = List.of(londonJakarta);
            initService.initDestination(destExternal3);
            generateScheduleFirstClass(destExternal3, londonFc.getAirplaneId());
        }


    }

    private Set<Seats> generateSeatsFirstClass() {
        int idClass = firstClass.getClassId();
        return new HashSet<>(Arrays.asList(new Seats("A1", 'A', idClass, 1), new Seats("A2", 'A', idClass, 2), new Seats("A3", 'A', idClass, 3), new Seats("A4", 'A', idClass, 4), new Seats("A5", 'A', idClass, 5)
                , new Seats("B1", 'B', idClass, 1), new Seats("B2", 'B', idClass, 2), new Seats("B3", 'B', idClass, 3), new Seats("B4", 'B', idClass, 4), new Seats("B5", 'B', idClass, 5)));
    }

    private Set<Seats> generateSeats(int idClass, int idClass2) {
        return new HashSet<>(Arrays.asList(new Seats("A1", 'A', idClass, 1), new Seats("A2", 'A', idClass, 2), new Seats("A3", 'A', idClass, 3), new Seats("A4", 'A', idClass, 4), new Seats("A5", 'A', idClass, 5), new Seats("A6", 'A', idClass, 6), new Seats("A7", 'A', idClass, 7), new Seats("A8", 'A', idClass, 8), new Seats("A9", 'A', idClass, 9), new Seats("A10", 'A', idClass, 10)
                , new Seats("B1", 'B', idClass, 1), new Seats("B2", 'B', idClass, 2), new Seats("B3", 'B', idClass, 3), new Seats("B4", 'B', idClass, 4), new Seats("B5", 'B', idClass, 5), new Seats("B6", 'B', idClass, 6), new Seats("B7", 'B', idClass, 7), new Seats("B8", 'B', idClass, 8), new Seats("B9", 'B', idClass, 9), new Seats("B10", 'B', idClass, 10)
                , new Seats("C1", 'C', idClass, 1), new Seats("C2", 'C', idClass, 2), new Seats("C3", 'C', idClass, 3), new Seats("C4", 'C', idClass, 4), new Seats("C5", 'C', idClass, 5), new Seats("C6", 'C', idClass, 6), new Seats("C7", 'C', idClass, 7), new Seats("C8", 'C', idClass, 8), new Seats("C9", 'C', idClass, 9), new Seats("C10", 'C', idClass, 10)
                , new Seats("D1", 'D', idClass, 1), new Seats("D2", 'D', idClass, 2), new Seats("D3", 'D', idClass, 3), new Seats("D4", 'D', idClass, 4), new Seats("D5", 'D', idClass, 5), new Seats("D6", 'D', idClass, 6), new Seats("D7", 'D', idClass, 7), new Seats("D8", 'D', idClass, 8), new Seats("D9", 'D', idClass, 9), new Seats("D10", 'D', idClass, 10),
                new Seats("A11", 'A', idClass2, 11), new Seats("A12", 'A', idClass2, 12), new Seats("A13", 'A', idClass2, 13), new Seats("A14", 'A', idClass2, 14), new Seats("A15", 'A', idClass2, 15), new Seats("A16", 'A', idClass2, 16), new Seats("A17", 'A', idClass2, 17), new Seats("A18", 'A', idClass2, 18), new Seats("A19", 'A', idClass2, 19), new Seats("A20", 'A', idClass2, 20)
                , new Seats("B11", 'B', idClass2, 11), new Seats("B12", 'B', idClass2, 12), new Seats("B13", 'B', idClass2, 13), new Seats("B14", 'B', idClass2, 14), new Seats("B15", 'B', idClass2, 15), new Seats("B16", 'B', idClass2, 16), new Seats("B17", 'B', idClass2, 17), new Seats("B18", 'B', idClass2, 18), new Seats("B19", 'B', idClass2, 19), new Seats("B20", 'B', idClass2, 20)
                , new Seats("C11", 'C', idClass2, 11), new Seats("C12", 'C', idClass2, 12), new Seats("C13", 'C', idClass2, 13), new Seats("C14", 'C', idClass2, 14), new Seats("C15", 'C', idClass2, 15), new Seats("C16", 'C', idClass2, 16), new Seats("C17", 'C', idClass2, 17), new Seats("C18", 'C', idClass2, 18), new Seats("C19", 'C', idClass2, 19), new Seats("C20", 'C', idClass2, 20)
                , new Seats("D11", 'D', idClass2, 11), new Seats("D12", 'D', idClass2, 12), new Seats("D13", 'D', idClass2, 13), new Seats("D14", 'D', idClass2, 14), new Seats("D15", 'D', idClass2, 15), new Seats("D16", 'D', idClass2, 16), new Seats("D17", 'D', idClass2, 17), new Seats("D18", 'D', idClass2, 18), new Seats("D19", 'D', idClass2, 19), new Seats("D20", 'D', idClass2, 20)));
    }

    private void generateScheduleFirstClass(List<Destination> destinations, int airplane1) {
        int priceFc = firstClassPrice.getPricingId();
        for (Destination req : destinations) {
            int destinationId = req.getDestinationId();
            for (long i = 0; i <= 365; i++) {
                LocalDate flighDate = LocalDate.now().plusDays(i);
                Schedule schedule1 = new Schedule(destinationId, firstClass.getClassId(), priceFc, LocalTime.of(7, 30), LocalTime.of(7, 40).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 785000, Constant.INTER));
                Schedule schedule2 = new Schedule(destinationId, firstClass.getClassId(), priceFc, LocalTime.of(19, 30), LocalTime.of(19, 30).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 915000, Constant.INTER));
                initService.initSchedule(Arrays.asList(schedule1, schedule2));
            }
        }
    }

    private void generateSchedule(List<Destination> destinations, int airplane1, int airplane2) {
        int priceEconomy = economiPrice.getPricingId();
        int priceBusiness = businessPrice.getPricingId();
        for (Destination req : destinations) {
            int destinationId = req.getDestinationId();
            for (long i = 0; i <= 365; i++) {
                LocalDate flighDate = LocalDate.now().plusDays(i);
                //Economy
                Schedule schedule = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 5000, Constant.DOMESTIK));
                Schedule schedule1 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(5, 30), LocalTime.of(5, 30).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane2, 17000, Constant.DOMESTIK));
                Schedule schedule2 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(11, 30), LocalTime.of(11, 30).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 24000, Constant.DOMESTIK));
                Schedule schedule3 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(15, 0), LocalTime.of(15, 0).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane2, 34000, Constant.DOMESTIK));
                Schedule schedule4 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(16, 45), LocalTime.of(16, 45).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 40000, Constant.DOMESTIK));

                //Business
                Schedule schedule5 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 10000, Constant.DOMESTIK));
                Schedule schedule6 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(5, 30), LocalTime.of(5, 30).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane2, 27000, Constant.DOMESTIK));
                Schedule schedule7 = new Schedule(destinationId, businessClass.getClassId(), priceEconomy, LocalTime.of(11, 30), LocalTime.of(11, 30).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 42000, Constant.DOMESTIK));
                Schedule schedule8 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(15, 0), LocalTime.of(15, 0).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane2, 51000, Constant.DOMESTIK));
                Schedule schedule9 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(16, 45), LocalTime.of(16, 45).plusMinutes(req.getDuration()), flighDate, new ScheduleAirplane(airplane1, 60000, Constant.DOMESTIK));
                initService.initSchedule(Arrays.asList(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, schedule8, schedule9));
            }
        }
    }
}

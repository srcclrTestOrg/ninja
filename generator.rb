class Generator
  def generate
    FileUtils.mkdir_p(Rails.root.join('lib').join)
    calculate("accounts.csv") do |tran|
      id = tran[:id]
      dues = []

      #dues << handling_fee_due

      outstanding_principal = tran[:amount]
      management_fee = (tran[:amount] * tran[:management_rate] * 30).round(2)
      installment_count(tran[:tenor]).times do |index|

        #
        interest = (outstanding_principal * tran[:interest_rate * 30]).round(2)

        principal = (LoanCalculatorV3.new(tran[:amount], tran[:tenor], tran[:interest_rate], tran[:management_rate]).installment - management_fee - interest).round(2)
        principal = outstanding_principal if (outstanding_principal - principal).abs < 1

        dues << Due.new(amount: management_fee, index: index + 1, due_type: 'management_fee')
        dues << Due.new(amount: interest, index: index + 1, due_type: 'interest')
        dues << Due.new(amount: principal, index: index + 1, due_type: 'principal')

        outstanding_principal -= principal
      end

      dues

      csv_file = Rails.root.join('private').join('correct_data.csv').to_s
      puts "Exporting calculate report"
      CSV.open(csv_file, "wb") do |csv|
        csv << %w(id due_type index amount)
        dues.each do |due|
          csv<< [tran[:id],due.due_type,due.index,due.amount]
        end
      end

    end
  end

  def installment_count(tenor)
    if tenor.ends_with? 'M'
      tenor.to_i
    elsif tenor.ends_with? 'D'
      1
    end
  end


  def calculate(filename)
    FileUtils.mkdir_p(CSV_PATH)
    file_path = CSV_PATH.join(filename).to_s
    f = File.open(file_path, "r:bom|utf-8")

    SmarterCSV.process(f) do |trans|
      trans.each do |tran|
        yield(tran)
      end
    end

    f.close
  end

end

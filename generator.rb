class Generator
  CSV_PATH = Rails.root.join('lib').join('due_generator')
  class << self

    def generate
      #FileUtils.mkdir_p(Rails.root.join('lib').join('due_generator'))
      dues = []
      calculate("1.csv") do |tran|
        id = tran[:id]


        #dues << handling_fee_due
        amount = tran[:amount].to_f
        management_rate = tran[:management_rate].to_f
        outstanding_principal = tran[:amount].to_f
        management_fee = ( amount * management_rate * 30).round(2)
        interest_rate = tran[:interest_rate].to_f

        installment_count(tran[:tenor]).times do |index|

          #
          interest = (outstanding_principal *interest_rate * 30).round(2)

          principal = (LoanCalculatorV3.new(amount, tran[:tenor], interest_rate, management_rate).installment - management_fee - interest).round(2)
          principal = outstanding_principal if (outstanding_principal - principal).abs < 1
          dues << Due.new(loan_id:tran[:id])
          dues << Due.new(amount: management_fee, index: index + 1, due_type: 'management_fee')
          dues << Due.new(amount: interest, index: index + 1, due_type: 'interest')
          dues << Due.new(amount: principal, index: index + 1, due_type: 'principal')

          outstanding_principal -= principal
        end
        csv_file = Rails.root.join('private').join('correct_data.csv').to_s
        puts "Exporting calculate report"
        CSV.open(csv_file, "wb") do |csv|
          csv << %w(id due_type index amount)
          dues.each do |due|
            csv<< [due.loan_id,due.due_type,due.index,due.amount]
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
      byebug
      f = File.open(file_path, "r:bom|utf-8")

      SmarterCSV.process(f) do |trans|
        trans.each do |tran|
          yield(tran)
        end
      end

      f.close
    end
  end
end
